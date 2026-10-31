package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppScreen
import com.example.data.AppUiState
import com.example.data.AppViewModel
import com.example.model.QuestionType
import com.example.model.QuizQuestion
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
  viewModel: AppViewModel,
  uiState: AppUiState,
  modifier: Modifier = Modifier
) {
  val quizState = uiState.quizState
  val pdf = quizState?.pdfDocument ?: uiState.selectedPdf

  if (quizState == null || pdf == null) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      Text("Imtixaan ma socdo hadda. Fadlan ka dooro Dashboard-ka.")
    }
    return
  }

  val currentQuestion: QuizQuestion? = pdf.questions.getOrNull(quizState.currentQuestionIndex)
  val minutes = quizState.timeRemainingSeconds / 60
  val seconds = quizState.timeRemainingSeconds % 60
  val formattedTimer = String.format("%02d:%02d", minutes, seconds)

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "QUIZ: ${pdf.chapter.uppercase()}",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                color = SomaliBluePrimary
              ),
              maxLines = 1
            )
            Text(
              text = pdf.title,
              style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle),
              maxLines = 1
            )
          }
        },
        navigationIcon = {
          IconButton(onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SomaliBluePrimary)
          }
        },
        actions = {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFFFF3E0),
            border = androidx.compose.foundation.BorderStroke(1.dp, SomaliGoldCrown)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Icon(Icons.Default.Timer, contentDescription = null, tint = SomaliGoldCrown, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = formattedTimer,
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFE65100)
                )
              )
            }
          }
          Spacer(modifier = Modifier.width(8.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
      )
    },
    bottomBar = {
      // Bottom Score & Scan/Submit Bar matching mockup
      Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Score: ",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = SomaliTextSubtle
              )
            )
            Text(
              text = "${quizState.score}",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = SomaliBluePrimary
              )
            )
          }

          if (!quizState.isAnswerSubmitted) {
            Button(
              onClick = { viewModel.submitAnswer() },
              enabled = quizState.selectedAnswer != null || quizState.selectedWordBankWord != null,
              colors = ButtonDefaults.buttonColors(containerColor = SomaliBluePrimary),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.testTag("submit_answer_button")
            ) {
              Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Hubi / Scan Answer", fontWeight = FontWeight.Bold)
            }
          } else {
            Button(
              onClick = { viewModel.nextQuestion() },
              colors = ButtonDefaults.buttonColors(containerColor = SomaliSuccessGreen),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.testTag("next_question_button")
            ) {
              Text("Su'aasha Xigta", fontWeight = FontWeight.Bold)
              Spacer(modifier = Modifier.width(6.dp))
              Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
          }
        }
      }
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {
      // Tags matching mockup
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = Color(0xFFE3F2FD),
          border = androidx.compose.foundation.BorderStroke(1.dp, SomaliSkyBlue)
        ) {
          Text(
            text = "ANNOTATED PDF",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = SomaliBluePrimary,
              fontSize = 10.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = Color(0xFFE8F5E9),
          border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBrightGreen)
        ) {
          Text(
            text = "SOMALI EXAM ENGINE",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = SomaliSuccessGreen,
              fontSize = 10.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Questions extracted from annotated PDF question sheet.",
        style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle)
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Progress bar (Su'aal X of Y)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Su'aasha ${quizState.currentQuestionIndex + 1} / ${quizState.totalQuestions}",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = SomaliBluePrimary
          )
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      LinearProgressIndicator(
        progress = (quizState.currentQuestionIndex + 1).toFloat() / quizState.totalQuestions.coerceAtLeast(1),
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = SomaliBluePrimary,
        trackColor = Color(0xFFE0E0E0)
      )

      Spacer(modifier = Modifier.height(16.dp))

      if (currentQuestion != null) {
        // Question Card
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            // Type badge
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = Color(0xFFF0F4F8)
            ) {
              Text(
                text = when (currentQuestion.type) {
                  QuestionType.MULTIPLE_CHOICE -> "MULTIPLE CHOICE (Option A, B, C, D)"
                  QuestionType.TRUE_FALSE -> "TRUE OR FALSE (Sax iyo Khalad)"
                  QuestionType.FILL_BLANKS -> "FILL IN THE BLANKS (Hal-Click Buuxi)"
                },
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = SomaliBluePrimary,
                  fontSize = 11.sp
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // English Prompt
            Text(
              text = currentQuestion.prompt,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )

            // Somali Prompt
            currentQuestion.somaliPrompt?.let { somaliText ->
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = somaliText,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Normal,
                  color = SomaliTextSubtle,
                  fontSize = 13.sp
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dynamic Options based on QuestionType
        when (currentQuestion.type) {
          QuestionType.MULTIPLE_CHOICE -> {
            MultipleChoiceSection(
              options = currentQuestion.options,
              selectedAnswer = quizState.selectedAnswer,
              correctAnswer = currentQuestion.correctAnswer,
              isSubmitted = quizState.isAnswerSubmitted,
              onSelect = { viewModel.selectQuizAnswer(it) }
            )
          }

          QuestionType.TRUE_FALSE -> {
            TrueFalseSection(
              selectedAnswer = quizState.selectedAnswer,
              correctAnswer = currentQuestion.correctAnswer,
              isSubmitted = quizState.isAnswerSubmitted,
              onSelect = { viewModel.selectQuizAnswer(it) }
            )
          }

          QuestionType.FILL_BLANKS -> {
            FillInTheBlanksSection(
              blankSentence = currentQuestion.blankSentence ?: "Statement: ______",
              selectedWord = quizState.selectedWordBankWord,
              wordBank = currentQuestion.wordBank,
              correctAnswer = currentQuestion.correctAnswer,
              isSubmitted = quizState.isAnswerSubmitted,
              onWordSelected = { viewModel.selectWordBankChip(it) }
            )
          }
        }

        // Explanation Card (Visible after submit)
        AnimatedVisibility(visible = quizState.isAnswerSubmitted) {
          Column(modifier = Modifier.padding(top = 16.dp)) {
            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (quizState.isAnswerCorrect) SomaliGreenContainer else Color(0xFFFFEBEE)
              ),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (quizState.isAnswerCorrect) SomaliSuccessGreen else SomaliErrorRed
              ),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.Top
              ) {
                Icon(
                  imageVector = if (quizState.isAnswerCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                  contentDescription = null,
                  tint = if (quizState.isAnswerCorrect) SomaliSuccessGreen else SomaliErrorRed,
                  modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = if (quizState.isAnswerCorrect) "Waad Saxday! (Correct Answer)" else "Waa Khalad (Incorrect)",
                    style = MaterialTheme.typography.titleSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = if (quizState.isAnswerCorrect) SomaliSuccessGreen else SomaliErrorRed
                    )
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = currentQuestion.explanation,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                  )
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(80.dp))
    }
  }

  // Quiz Completed Dialog
  if (quizState.isQuizCompleted) {
    AlertDialog(
      onDismissRequest = { viewModel.navigateTo(AppScreen.DASHBOARD) },
      icon = {
        Icon(
          Icons.Default.EmojiEvents,
          contentDescription = null,
          tint = SomaliGoldCrown,
          modifier = Modifier.size(48.dp)
        )
      },
      title = {
        Text(
          text = "Hambalyo! Imtixaankii Waad Dhammaysatay",
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )
      },
      text = {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "Dhibcahaaga: ${quizState.score} dhibcood",
            style = MaterialTheme.typography.headlineSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              color = SomaliBluePrimary
            )
          )
          Text(
            text = "Waxaad kordhisay horumarkaaga guud ee Fasalka 4-aad!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
          )
          Text(
            text = "Streak-kaaga: +1 Maalin 🔥",
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
          colors = ButtonDefaults.buttonColors(containerColor = SomaliBluePrimary)
        ) {
          Text("Ku Noqo Dashboard")
        }
      }
    )
  }
}

@Composable
fun MultipleChoiceSection(
  options: List<String>,
  selectedAnswer: String?,
  correctAnswer: String,
  isSubmitted: Boolean,
  onSelect: (String) -> Unit
) {
  val labels = listOf("A", "B", "C", "D")
  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    options.forEachIndexed { index, option ->
      val isSelected = selectedAnswer == option
      val isThisCorrect = option == correctAnswer

      val borderColor = when {
        isSubmitted && isThisCorrect -> SomaliSuccessGreen
        isSubmitted && isSelected && !isThisCorrect -> SomaliErrorRed
        isSelected -> SomaliBluePrimary
        else -> SomaliBorder
      }

      val bgColor = when {
        isSubmitted && isThisCorrect -> SomaliGreenContainer
        isSubmitted && isSelected && !isThisCorrect -> Color(0xFFFFEBEE)
        isSelected -> Color(0xFFE3F2FD)
        else -> Color.White
      }

      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
        modifier = Modifier
          .fillMaxWidth()
          .clickable(enabled = !isSubmitted) { onSelect(option) }
          .testTag("option_card_$index")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(if (isSelected) SomaliBluePrimary else Color(0xFFECEFF1)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = labels.getOrElse(index) { "${index + 1}" },
              fontWeight = FontWeight.Bold,
              color = if (isSelected) Color.White else Color.Black,
              fontSize = 13.sp
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Text(
            text = option,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.weight(1f)
          )

          if (isSubmitted && isThisCorrect) {
            Icon(Icons.Default.Check, contentDescription = "Correct", tint = SomaliSuccessGreen)
          }
        }
      }
    }
  }
}

@Composable
fun TrueFalseSection(
  selectedAnswer: String?,
  correctAnswer: String,
  isSubmitted: Boolean,
  onSelect: (String) -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    listOf("Sax (True)", "Khalad (False)").forEach { option ->
      val isSelected = selectedAnswer == option
      val isThisCorrect = correctAnswer.startsWith(option.substringBefore(" "))

      val borderColor = when {
        isSubmitted && isThisCorrect -> SomaliSuccessGreen
        isSubmitted && isSelected && !isThisCorrect -> SomaliErrorRed
        isSelected -> SomaliBluePrimary
        else -> SomaliBorder
      }

      val bgColor = when {
        isSubmitted && isThisCorrect -> SomaliGreenContainer
        isSubmitted && isSelected && !isThisCorrect -> Color(0xFFFFEBEE)
        isSelected -> Color(0xFFE3F2FD)
        else -> Color.White
      }

      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
        modifier = Modifier
          .weight(1f)
          .height(70.dp)
          .clickable(enabled = !isSubmitted) { onSelect(option) }
          .testTag("tf_card_${option.take(3)}")
      ) {
        Row(
          modifier = Modifier.fillMaxSize(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          RadioButton(
            selected = isSelected,
            onClick = { if (!isSubmitted) onSelect(option) }
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = option,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FillInTheBlanksSection(
  blankSentence: String,
  selectedWord: String?,
  wordBank: List<String>,
  correctAnswer: String,
  isSubmitted: Boolean,
  onWordSelected: (String) -> Unit
) {
  Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    // Sentence card with highlighted blank slot
    Card(
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBFF)),
      border = androidx.compose.foundation.BorderStroke(1.dp, SomaliSkyBlue.copy(alpha = 0.5f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "Weedha buuxi:",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = SomaliBluePrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))

        // Replace blank underscores with active selected word tag
        val parts = blankSentence.split("______")
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = parts.getOrNull(0) ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp)
          )
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (selectedWord != null) SomaliBluePrimary.copy(alpha = 0.15f) else Color(0xFFEEEEEE),
            border = androidx.compose.foundation.BorderStroke(
              1.5.dp,
              if (selectedWord != null) SomaliBluePrimary else Color.Gray
            ),
            modifier = Modifier.padding(horizontal = 4.dp)
          ) {
            Text(
              text = selectedWord ?: " [ Doorasho ] ",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = if (selectedWord != null) SomaliBluePrimary else Color.Gray
              ),
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
          Text(
            text = parts.getOrNull(1) ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp)
          )
        }
      }
    }

    // Word Bank Section
    Text(
      text = "KALIYA HAL-CLICK KU DOORASHO (WORD BANK):",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Black,
        color = SomaliTextDark,
        letterSpacing = 0.5.sp
      )
    )

    // Interactive chips
    androidx.compose.foundation.layout.FlowRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      wordBank.forEach { word ->
        val isChosen = selectedWord?.equals(word, ignoreCase = true) == true
        InputChip(
          selected = isChosen,
          onClick = { if (!isSubmitted) onWordSelected(word) },
          label = {
            Text(
              text = word,
              fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Medium,
              fontSize = 14.sp
            )
          },
          colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = SomaliBluePrimary,
            selectedLabelColor = Color.White
          ),
          border = InputChipDefaults.inputChipBorder(
            enabled = true,
            selected = isChosen,
            borderColor = if (isChosen) SomaliBluePrimary else SomaliBorder
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("word_chip_$word")
        )
      }
    }
  }
}
