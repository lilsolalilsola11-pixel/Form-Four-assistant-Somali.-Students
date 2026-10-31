package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppScreen
import com.example.data.AppUiState
import com.example.data.AppViewModel
import com.example.data.SampleData
import com.example.model.PdfDocument
import com.example.ui.components.CircularProgressMeter
import com.example.ui.components.SomaliStudentAvatar
import com.example.ui.components.SubjectProgressRing
import com.example.ui.components.WinnerBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
  viewModel: AppViewModel,
  uiState: AppUiState,
  modifier: Modifier = Modifier
) {
  var selectedSubjectFilter by remember { mutableStateOf<String?>(null) }
  var newPdfTitle by remember { mutableStateOf("") }
  var newPdfSubject by remember { mutableStateOf("Biology") }

  val filteredPdfs = if (selectedSubjectFilter == null) {
    uiState.pdfs
  } else {
    uiState.pdfs.filter { it.subject.contains(selectedSubjectFilter!!, ignoreCase = true) }
  }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = SomaliSoftBg,
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = { viewModel.openUploadPdfDialog(true) },
        icon = { Icon(Icons.Default.UploadFile, contentDescription = null) },
        text = { Text("Soo geli PDF", fontWeight = FontWeight.Bold) },
        containerColor = SomaliSuccessGreen,
        contentColor = Color.White,
        modifier = Modifier.testTag("upload_pdf_fab")
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Welcome Header
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SomaliBluePrimary),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.navigateTo(AppScreen.SETTINGS) }
          .testTag("welcome_card")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "WELCOME,",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = SomaliSkyBlue,
                letterSpacing = 1.sp
              )
            )
            Text(
              text = "${uiState.userProfile.fullName.substringBefore(" ").uppercase()}!",
              style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Black,
                color = Color.White
              )
            )
            Text(
              text = uiState.userProfile.school,
              style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White.copy(alpha = 0.85f)
              ),
              maxLines = 1
            )
          }

          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(CircleShape)
              .border(2.dp, SomaliGold, CircleShape)
              .background(SomaliBlueLight),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = uiState.userProfile.fullName.take(1),
              color = Color.White,
              fontWeight = FontWeight.ExtraBold,
              fontSize = 22.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Circular Total Progress & Rankcard Section
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Circular Progress Meter
          CircularProgressMeter(
            progress = uiState.userProfile.totalProgress,
            size = 140.dp,
            strokeWidth = 14.dp,
            primaryColor = SomaliBluePrimary,
            secondaryColor = SomaliBrightGreen,
            label = "TOTAL PROGRESS"
          )

          Spacer(modifier = Modifier.height(18.dp))

          // Subject Rings Grid (Math 60%, Physics 75%, Biology 40%, Chemistry 35%)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
          ) {
            SampleData.subjectProgressList.take(4).forEach { subject ->
              SubjectProgressRing(
                title = subject.subject,
                percent = (subject.progress * 100).toInt(),
                color = Color(subject.colorHex),
                onClick = {
                  selectedSubjectFilter = if (selectedSubjectFilter == subject.subject) null else subject.subject
                }
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Rankcard: Highest Score & Streak
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF1F6FD),
            border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              horizontalArrangement = Arrangement.SpaceAround,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "HIGHEST SCORE",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = SomaliTextSubtle
                  )
                )
                Text(
                  text = "${uiState.userProfile.highestScore} pts",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = SomaliBluePrimary
                  )
                )
              }

              VerticalDivider(modifier = Modifier.height(36.dp))

              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "BEST STREAK",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = SomaliTextSubtle
                  )
                )
                Text(
                  text = "${uiState.userProfile.currentStreak} Days 🔥",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFE65100)
                  )
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Section: MY PDFs
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "MY PDFs (Buugaagta & Imtixaannada)",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Black,
              color = SomaliBluePrimary
            )
          )
          Text(
            text = "Dooro casharka aad rabto inaad ka qaadato Quiz toos ah",
            style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle)
          )
        }

        IconButton(onClick = { viewModel.openUploadPdfDialog(true) }) {
          Icon(Icons.Default.AddCircle, contentDescription = "Add PDF", tint = SomaliSuccessGreen)
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // PDF Filter Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        item {
          FilterChip(
            selected = selectedSubjectFilter == null,
            onClick = { selectedSubjectFilter = null },
            label = { Text("Dhamaan (${uiState.pdfs.size})") }
          )
        }
        listOf("Biology", "Physics", "Chemistry", "Math", "Somali", "History").forEach { subj ->
          item {
            FilterChip(
              selected = selectedSubjectFilter?.contains(subj, ignoreCase = true) == true,
              onClick = {
                selectedSubjectFilter = if (selectedSubjectFilter == subj) null else subj
              },
              label = { Text(subj) }
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // PDF Cards Grid (History, Biology, Physics, Chemistry, Somali Lang, Food, Geography)
      Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        filteredPdfs.forEach { pdf ->
          PdfItemCard(
            pdf = pdf,
            onStartQuiz = { viewModel.startQuiz(pdf) }
          )
        }
      }

      Spacer(modifier = Modifier.height(40.dp))
    }
  }

  // Upload PDF Dialog
  if (uiState.showUploadPdfDialog) {
    AlertDialog(
      onDismissRequest = { viewModel.openUploadPdfDialog(false) },
      icon = { Icon(Icons.Default.CloudUpload, contentDescription = null, tint = SomaliBluePrimary) },
      title = { Text("Soo geli PDF Cusub (Upload PDF)", fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Text(
            text = "Dooro faylka PDF-ka ama ku qor magaca cutubka aad rabto in app-ku si otomatik ah uga soo saaro su'aalo (MCQ, True/False, Fill in Blanks):",
            style = MaterialTheme.typography.bodyMedium
          )
          OutlinedTextField(
            value = newPdfTitle,
            onValueChange = { newPdfTitle = it },
            label = { Text("Magaca PDF-ka / Cutubka") },
            placeholder = { Text("tusaale: Biology Chapter 3 - Ecology") },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newPdfSubject,
            onValueChange = { newPdfSubject = it },
            label = { Text("Maadada (Subject)") },
            placeholder = { Text("Biology / Physics / Math") },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.addCustomPdf(newPdfTitle, newPdfSubject)
            newPdfTitle = ""
          },
          colors = ButtonDefaults.buttonColors(containerColor = SomaliSuccessGreen)
        ) {
          Text("Soo saar Imtixaan (Generate Quiz)")
        }
      },
      dismissButton = {
        TextButton(onClick = { viewModel.openUploadPdfDialog(false) }) {
          Text("Jooji")
        }
      }
    )
  }
}

@Composable
fun PdfItemCard(
  pdf: PdfDocument,
  onStartQuiz: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onStartQuiz() }
      .testTag("pdf_card_${pdf.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(SomaliBluePrimary.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Description,
          contentDescription = null,
          tint = SomaliBluePrimary
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = pdf.title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            maxLines = 1
          )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color(0xFFE8F5E9)
          ) {
            Text(
              text = "ANNOTATED PDF",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = SomaliSuccessGreen,
                fontSize = 9.sp
              ),
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            )
          }
          Text(
            text = "${pdf.pages} bog • ${pdf.questions.size} Su'aalood",
            style = MaterialTheme.typography.bodySmall.copy(
              color = SomaliTextSubtle,
              fontSize = 11.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      Button(
        onClick = onStartQuiz,
        colors = ButtonDefaults.buttonColors(containerColor = SomaliBluePrimary),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        modifier = Modifier.testTag("quiz_btn_${pdf.id}")
      ) {
        Text("Quiz", fontWeight = FontWeight.Bold, fontSize = 12.sp)
      }
    }
  }
}
