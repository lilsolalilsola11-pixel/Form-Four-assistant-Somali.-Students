package com.example.model

enum class QuestionType {
  MULTIPLE_CHOICE,
  TRUE_FALSE,
  FILL_BLANKS
}

data class QuizQuestion(
  val id: String,
  val type: QuestionType,
  val prompt: String,
  val somaliPrompt: String? = null,
  val options: List<String> = emptyList(), // For MC and TF
  val correctAnswer: String,
  val blankSentence: String? = null, // e.g. "Only word not ever ______ in sentence."
  val wordBank: List<String> = emptyList(), // For Fill in the blanks: [chose, word, common, work, count, more]
  val explanation: String
)

data class PdfDocument(
  val id: String,
  val title: String,
  val subject: String,
  val chapter: String,
  val pages: Int,
  val isAnnotated: Boolean = true,
  val iconName: String = "book",
  val questions: List<QuizQuestion> = emptyList()
)

data class UserProfile(
  val id: String = "u_abdikadir",
  val fullName: String = "Abdikadir Nur",
  val username: String = "abdikadir_so",
  val bio: String = "Arday u diyaargaroobaya Imtixaanka Qaranka Fasalka 4-aad 2026. Hadafkeygu waa Kaalinta 1-aad Insha'Allah!",
  val school: String = "21 Oktoobar Secondary School",
  val city: String = "Muqdisho",
  val coverDrawableRes: String = "somali_school_cover",
  val avatarInitials: String = "AN",
  val totalProgress: Float = 0.68f, // 68%
  val highestScore: Int = 980,
  val currentStreak: Int = 14, // 14 days
  val quizzesCompleted: Int = 42,
  val battlesWon: Int = 9,
  val winnerBadgeTitle: String = "Kaalinta 1-aad (Gold Champion)"
)

data class SubjectProgress(
  val subject: String,
  val progress: Float, // 0.0 to 1.0
  val colorHex: Long,
  val iconName: String,
  val totalQuizzes: Int,
  val completedQuizzes: Int
)

data class LeaderboardUser(
  val rank: Int,
  val name: String,
  val username: String,
  val school: String,
  val score: Int,
  val streak: Int,
  val badge: String,
  val isCurrentUser: Boolean = false
)

data class StudyRoom(
  val id: String,
  val name: String,
  val subject: String,
  val hostName: String,
  val currentPlayers: Int,
  val maxPlayers: Int = 15,
  val hasPassword: Boolean = false,
  val password: String = "",
  val isOpen: Boolean = true
)

data class BattleParticipant(
  val id: String,
  val name: String,
  val school: String,
  var score: Int,
  var streak: Int,
  val avatarColor: Long,
  val isUser: Boolean = false
)

data class ChatMessage(
  val id: String,
  val senderName: String,
  val senderUsername: String,
  val text: String,
  val timestamp: String,
  val badge: String? = null,
  val subjectTag: String? = null,
  val isCurrentUser: Boolean = false
)
