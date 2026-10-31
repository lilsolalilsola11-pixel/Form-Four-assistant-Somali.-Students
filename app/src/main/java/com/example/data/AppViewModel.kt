package com.example.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppScreen {
  AUTH,
  DASHBOARD,
  QUIZ,
  ROOMS,
  MULTIPLAYER_BATTLE,
  CHAT,
  SETTINGS
}

enum class LeaderboardTab {
  HIGHEST_SCORE,
  BEST_STREAK
}

data class QuizState(
  val pdfDocument: PdfDocument,
  val currentQuestionIndex: Int = 0,
  val selectedAnswer: String? = null,
  val selectedWordBankWord: String? = null,
  val isAnswerSubmitted: Boolean = false,
  val isAnswerCorrect: Boolean = false,
  val score: Int = 0,
  val totalQuestions: Int = 0,
  val timeRemainingSeconds: Int = 180,
  val isQuizCompleted: Boolean = false
)

data class AppUiState(
  val currentScreen: AppScreen = AppScreen.DASHBOARD,
  val isAuthenticated: Boolean = true,
  val userProfile: UserProfile = UserProfile(),
  val pdfs: List<PdfDocument> = SampleData.samplePdfs,
  val selectedPdf: PdfDocument? = SampleData.samplePdfs.firstOrNull(),
  val quizState: QuizState? = null,
  val leaderboardTab: LeaderboardTab = LeaderboardTab.HIGHEST_SCORE,
  val rooms: List<StudyRoom> = SampleData.initialRooms,
  val battlePeers: List<BattleParticipant> = SampleData.initialBattlePeers,
  val battleCountdownFormatted: String = "01:03:48",
  val isBattleActive: Boolean = false,
  val battleQuestionIndex: Int = 0,
  val battleScore: Int = 0,
  val battleTimeRemaining: Int = 20,
  val battleCompleted: Boolean = false,
  val battleWinner: BattleParticipant? = null,
  val chatMessages: List<ChatMessage> = SampleData.initialChatMessages,
  val friendSearchQuery: String = "",
  val friendSearchResults: List<LeaderboardUser> = emptyList(),
  val showCreateRoomDialog: Boolean = false,
  val showUploadPdfDialog: Boolean = false,
  val showInviteDialog: Boolean = false,
  val inviteRoomName: String = "",
  val notificationMessage: String? = null
)

class AppViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(AppUiState())
  val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

  private var quizTimerJob: Job? = null
  private var battleSimulationJob: Job? = null

  fun navigateTo(screen: AppScreen) {
    _uiState.update { it.copy(currentScreen = screen) }
  }

  fun login(name: String = "Abdikadir Nur", username: String = "abdikadir_so") {
    _uiState.update {
      it.copy(
        isAuthenticated = true,
        userProfile = it.userProfile.copy(fullName = name, username = username),
        currentScreen = AppScreen.DASHBOARD
      )
    }
  }

  fun logout() {
    _uiState.update {
      it.copy(
        isAuthenticated = false,
        currentScreen = AppScreen.AUTH
      )
    }
  }

  fun setLeaderboardTab(tab: LeaderboardTab) {
    _uiState.update { it.copy(leaderboardTab = tab) }
  }

  fun searchFriends(query: String) {
    _uiState.update { state ->
      val filtered = if (query.isBlank()) {
        emptyList()
      } else {
        SampleData.scoreLeaderboard.filter {
          it.name.contains(query, ignoreCase = true) ||
          it.username.contains(query, ignoreCase = true) ||
          it.school.contains(query, ignoreCase = true)
        }
      }
      state.copy(friendSearchQuery = query, friendSearchResults = filtered)
    }
  }

  fun updateProfile(
    fullName: String,
    username: String,
    bio: String,
    school: String,
    city: String
  ) {
    _uiState.update { state ->
      state.copy(
        userProfile = state.userProfile.copy(
          fullName = fullName,
          username = username.removePrefix("@"),
          bio = bio,
          school = school,
          city = city
        ),
        notificationMessage = "Profile-ka si guul leh ayaa loo cusbooneysiiyay!"
      )
    }
  }

  fun startQuiz(pdf: PdfDocument) {
    val questions = if (pdf.questions.isNotEmpty()) pdf.questions else SampleData.biologyQuestions
    _uiState.update {
      it.copy(
        selectedPdf = pdf,
        currentScreen = AppScreen.QUIZ,
        quizState = QuizState(
          pdfDocument = pdf,
          currentQuestionIndex = 0,
          score = 0,
          totalQuestions = questions.size,
          timeRemainingSeconds = 180,
          isQuizCompleted = false
        )
      )
    }
    startQuizTimer()
  }

  private fun startQuizTimer() {
    quizTimerJob?.cancel()
    quizTimerJob = viewModelScope.launch {
      while (true) {
        delay(1000)
        val currentQuiz = _uiState.value.quizState ?: break
        if (currentQuiz.isQuizCompleted) break
        if (currentQuiz.timeRemainingSeconds <= 1) {
          finishQuiz()
          break
        } else {
          _uiState.update { state ->
            val quiz = state.quizState ?: return@update state
            state.copy(quizState = quiz.copy(timeRemainingSeconds = quiz.timeRemainingSeconds - 1))
          }
        }
      }
    }
  }

  fun selectQuizAnswer(answer: String) {
    _uiState.update { state ->
      val quiz = state.quizState ?: return@update state
      if (quiz.isAnswerSubmitted) return@update state
      quiz.pdfDocument.questions.getOrNull(quiz.currentQuestionIndex) ?: return@update state
      state.copy(quizState = quiz.copy(selectedAnswer = answer))
    }
  }

  fun selectWordBankChip(word: String) {
    _uiState.update { state ->
      val quiz = state.quizState ?: return@update state
      if (quiz.isAnswerSubmitted) return@update state
      state.copy(quizState = quiz.copy(selectedWordBankWord = word, selectedAnswer = word))
    }
  }

  fun submitAnswer() {
    val quiz = _uiState.value.quizState ?: return
    val currentQ = quiz.pdfDocument.questions.getOrNull(quiz.currentQuestionIndex) ?: return
    val chosen = quiz.selectedAnswer ?: quiz.selectedWordBankWord ?: return

    val isCorrect = chosen.equals(currentQ.correctAnswer, ignoreCase = true) ||
      chosen.startsWith(currentQ.correctAnswer, ignoreCase = true) ||
      currentQ.correctAnswer.startsWith(chosen, ignoreCase = true)

    val newScore = if (isCorrect) quiz.score + 10 else quiz.score

    _uiState.update { state ->
      val q = state.quizState ?: return@update state
      state.copy(
        quizState = q.copy(
          isAnswerSubmitted = true,
          isAnswerCorrect = isCorrect,
          score = newScore
        )
      )
    }
  }

  fun nextQuestion() {
    val quiz = _uiState.value.quizState ?: return
    val nextIndex = quiz.currentQuestionIndex + 1
    if (nextIndex >= quiz.pdfDocument.questions.size) {
      finishQuiz()
    } else {
      _uiState.update { state ->
        val q = state.quizState ?: return@update state
        state.copy(
          quizState = q.copy(
            currentQuestionIndex = nextIndex,
            selectedAnswer = null,
            selectedWordBankWord = null,
            isAnswerSubmitted = false,
            isAnswerCorrect = false
          )
        )
      }
    }
  }

  private fun finishQuiz() {
    quizTimerJob?.cancel()
    _uiState.update { state ->
      val q = state.quizState ?: return@update state
      val addedPoints = q.score * 10
      val updatedProfile = state.userProfile.copy(
        highestScore = maxOf(state.userProfile.highestScore, state.userProfile.highestScore + addedPoints),
        quizzesCompleted = state.userProfile.quizzesCompleted + 1,
        totalProgress = minOf(1.0f, state.userProfile.totalProgress + 0.05f)
      )
      state.copy(
        userProfile = updatedProfile,
        quizState = q.copy(isQuizCompleted = true),
        notificationMessage = "Imtixaanka waad dhammaysatay! Waxaad heshay ${q.score} dhibcood! 🎉"
      )
    }
  }

  // Online Rooms & 15-Player Battle
  fun openCreateRoomDialog(show: Boolean) {
    _uiState.update { it.copy(showCreateRoomDialog = show) }
  }

  fun openUploadPdfDialog(show: Boolean) {
    _uiState.update { it.copy(showUploadPdfDialog = show) }
  }

  fun openInviteDialog(show: Boolean, roomName: String = "") {
    _uiState.update { it.copy(showInviteDialog = show, inviteRoomName = roomName) }
  }

  fun createRoom(name: String, subject: String, password: String, maxPlayers: Int = 15) {
    val newRoom = StudyRoom(
      id = "room_${System.currentTimeMillis()}",
      name = name.ifBlank { "Qolka Waxbarashada Form 4" },
      subject = subject.ifBlank { "Guud ahaan" },
      hostName = _uiState.value.userProfile.fullName,
      currentPlayers = 1,
      maxPlayers = maxPlayers.coerceIn(2, 15),
      hasPassword = password.isNotBlank(),
      password = password,
      isOpen = true
    )
    _uiState.update { state ->
      state.copy(
        rooms = listOf(newRoom) + state.rooms,
        showCreateRoomDialog = false,
        notificationMessage = "Qolka '${newRoom.name}' si toos ah ayaa loo furay!"
      )
    }
  }

  fun joinRoom(room: StudyRoom) {
    _uiState.update { state ->
      val updatedRooms = state.rooms.map {
        if (it.id == room.id) it.copy(currentPlayers = minOf(it.maxPlayers, it.currentPlayers + 1)) else it
      }
      state.copy(
        rooms = updatedRooms,
        notificationMessage = "Waxaad ku biirtay qolka '${room.name}'!"
      )
    }
    // Launch multiplayer battle for that room
    startMultiplayerBattle()
  }

  fun startMultiplayerBattle() {
    val freshPeers = SampleData.initialBattlePeers.map {
      it.copy(
        score = 0,
        streak = 0
      )
    }
    _uiState.update {
      it.copy(
        currentScreen = AppScreen.MULTIPLAYER_BATTLE,
        isBattleActive = true,
        battleQuestionIndex = 0,
        battleScore = 0,
        battleTimeRemaining = 15,
        battleCompleted = false,
        battleWinner = null,
        battlePeers = freshPeers
      )
    }
    runBattleCycle()
  }

  private fun runBattleCycle() {
    battleSimulationJob?.cancel()
    battleSimulationJob = viewModelScope.launch {
      for (qIndex in 0 until 4) {
        _uiState.update {
          it.copy(
            battleQuestionIndex = qIndex,
            battleTimeRemaining = 12
          )
        }
        for (sec in 12 downTo 1) {
          delay(1000)
          _uiState.update { state ->
            // Simulate other 14 students answering randomly
            val updatedPeers = state.battlePeers.map { peer ->
              if (!peer.isUser && Random.nextFloat() > 0.65f) {
                peer.copy(score = peer.score + Random.nextInt(5, 12))
              } else peer
            }.sortedByDescending { it.score }
            state.copy(
              battleTimeRemaining = sec - 1,
              battlePeers = updatedPeers
            )
          }
        }
      }

      // Finish battle
      val finalLeaderboard = _uiState.value.battlePeers.sortedByDescending { it.score }
      val winner = finalLeaderboard.firstOrNull()
      val isUserWinner = winner?.isUser == true

      _uiState.update { state ->
        val updatedProfile = if (isUserWinner) {
          state.userProfile.copy(
            battlesWon = state.userProfile.battlesWon + 1,
            highestScore = state.userProfile.highestScore + 150,
            currentStreak = state.userProfile.currentStreak + 1
          )
        } else {
          state.userProfile.copy(highestScore = state.userProfile.highestScore + 50)
        }
        state.copy(
          isBattleActive = false,
          battleCompleted = true,
          battleWinner = winner,
          userProfile = updatedProfile,
          notificationMessage = if (isUserWinner) "Hambalyo! Waxaad gashay Kaalinta 1-aad! 👑🥇" else "Tartanku wuu dhammaaday! Guulwade: ${winner?.name}!"
        )
      }
    }
  }

  fun submitBattleAnswer(isCorrect: Boolean) {
    if (!_uiState.value.isBattleActive) return
    val bonus = if (isCorrect) 15 else 2
    _uiState.update { state ->
      val updatedPeers = state.battlePeers.map { peer ->
        if (peer.isUser) peer.copy(score = peer.score + bonus, streak = if (isCorrect) peer.streak + 1 else 0)
        else peer
      }.sortedByDescending { it.score }

      state.copy(
        battleScore = state.battleScore + bonus,
        battlePeers = updatedPeers
      )
    }
  }

  fun addCustomPdf(title: String, subject: String) {
    val cleanTitle = title.ifBlank { "PDF Cusub: Form Four Study Guide" }
    val newPdf = PdfDocument(
      id = "custom_${System.currentTimeMillis()}",
      title = cleanTitle,
      subject = subject.ifBlank { "General" },
      chapter = "Custom Module",
      pages = Random.nextInt(12, 35),
      isAnnotated = true,
      iconName = "picture_as_pdf",
      questions = SampleData.biologyQuestions.shuffled()
    )
    _uiState.update { state ->
      state.copy(
        pdfs = listOf(newPdf) + state.pdfs,
        showUploadPdfDialog = false,
        notificationMessage = "PDF-ka '${newPdf.title}' si guul leh ayaa loo soo geliyay, su'aalihiina waa la soo saaray!"
      )
    }
  }

  fun sendChatMessage(text: String, subjectTag: String = "General") {
    if (text.isBlank()) return
    val msg = ChatMessage(
      id = "msg_${System.currentTimeMillis()}",
      senderName = _uiState.value.userProfile.fullName,
      senderUsername = "@${_uiState.value.userProfile.username}",
      text = text,
      timestamp = "Hadda",
      badge = if (_uiState.value.userProfile.battlesWon > 0) "👑 Gold Champion" else "⭐ Student",
      subjectTag = subjectTag,
      isCurrentUser = true
    )
    _uiState.update { state ->
      state.copy(chatMessages = state.chatMessages + msg)
    }
  }

  fun dismissNotification() {
    _uiState.update { it.copy(notificationMessage = null) }
  }

  override fun onCleared() {
    super.onCleared()
    quizTimerJob?.cancel()
    battleSimulationJob?.cancel()
  }
}
