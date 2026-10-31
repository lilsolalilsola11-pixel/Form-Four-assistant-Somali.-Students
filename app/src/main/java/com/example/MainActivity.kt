package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AppScreen
import com.example.data.AppViewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SomaliBluePrimary
import com.example.ui.theme.SomaliBorder

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        FormFourApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormFourApp(viewModel: AppViewModel = viewModel()) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  // Show notification message if any
  LaunchedEffect(uiState.notificationMessage) {
    uiState.notificationMessage?.let { msg ->
      snackbarHostState.showSnackbar(msg)
      viewModel.dismissNotification()
    }
  }

  // Handle Android hardware/gesture back press
  BackHandler(enabled = uiState.currentScreen != AppScreen.DASHBOARD && uiState.currentScreen != AppScreen.AUTH) {
    when (uiState.currentScreen) {
      AppScreen.MULTIPLAYER_BATTLE -> viewModel.navigateTo(AppScreen.ROOMS)
      AppScreen.QUIZ -> viewModel.navigateTo(AppScreen.DASHBOARD)
      else -> viewModel.navigateTo(AppScreen.DASHBOARD)
    }
  }

  if (!uiState.isAuthenticated || uiState.currentScreen == AppScreen.AUTH) {
    AuthScreen(viewModel = viewModel, uiState = uiState)
  } else {
    Scaffold(
      modifier = Modifier.fillMaxSize(),
      snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
      bottomBar = {
        // Do not show bottom bar inside full-screen battle
        if (uiState.currentScreen != AppScreen.MULTIPLAYER_BATTLE) {
          NavigationBar(
            containerColor = Color.White,
            tonalElevation = 6.dp,
            modifier = Modifier
              .windowInsetsPadding(WindowInsets.navigationBars)
              .testTag("bottom_nav_bar")
          ) {
            NavigationBarItem(
              selected = uiState.currentScreen == AppScreen.DASHBOARD,
              onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
              icon = {
                Icon(
                  if (uiState.currentScreen == AppScreen.DASHBOARD) Icons.Default.Dashboard else Icons.Outlined.Dashboard,
                  contentDescription = "Dashboard"
                )
              },
              label = { Text("Dashboard", fontWeight = FontWeight.Bold) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SomaliBluePrimary,
                selectedTextColor = SomaliBluePrimary,
                indicatorColor = Color(0xFFE3F2FD)
              ),
              modifier = Modifier.testTag("nav_dashboard")
            )

            NavigationBarItem(
              selected = uiState.currentScreen == AppScreen.QUIZ,
              onClick = {
                if (uiState.quizState == null && uiState.pdfs.isNotEmpty()) {
                  viewModel.startQuiz(uiState.pdfs.first())
                } else {
                  viewModel.navigateTo(AppScreen.QUIZ)
                }
              },
              icon = {
                Icon(
                  if (uiState.currentScreen == AppScreen.QUIZ) Icons.Default.Quiz else Icons.Outlined.Quiz,
                  contentDescription = "Quiz"
                )
              },
              label = { Text("Quiz", fontWeight = FontWeight.Bold) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SomaliBluePrimary,
                selectedTextColor = SomaliBluePrimary,
                indicatorColor = Color(0xFFE3F2FD)
              ),
              modifier = Modifier.testTag("nav_quiz")
            )

            NavigationBarItem(
              selected = uiState.currentScreen == AppScreen.ROOMS,
              onClick = { viewModel.navigateTo(AppScreen.ROOMS) },
              icon = {
                Icon(
                  if (uiState.currentScreen == AppScreen.ROOMS) Icons.Default.Groups else Icons.Outlined.Groups,
                  contentDescription = "Rooms"
                )
              },
              label = { Text("Rooms", fontWeight = FontWeight.Bold) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SomaliBluePrimary,
                selectedTextColor = SomaliBluePrimary,
                indicatorColor = Color(0xFFE3F2FD)
              ),
              modifier = Modifier.testTag("nav_rooms")
            )

            NavigationBarItem(
              selected = uiState.currentScreen == AppScreen.CHAT,
              onClick = { viewModel.navigateTo(AppScreen.CHAT) },
              icon = {
                Icon(
                  if (uiState.currentScreen == AppScreen.CHAT) Icons.AutoMirrored.Filled.Chat else Icons.Outlined.Chat,
                  contentDescription = "Chat"
                )
              },
              label = { Text("Chat", fontWeight = FontWeight.Bold) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SomaliBluePrimary,
                selectedTextColor = SomaliBluePrimary,
                indicatorColor = Color(0xFFE3F2FD)
              ),
              modifier = Modifier.testTag("nav_chat")
            )

            NavigationBarItem(
              selected = uiState.currentScreen == AppScreen.SETTINGS,
              onClick = { viewModel.navigateTo(AppScreen.SETTINGS) },
              icon = {
                Icon(
                  if (uiState.currentScreen == AppScreen.SETTINGS) Icons.Default.Person else Icons.Outlined.Person,
                  contentDescription = "Profile"
                )
              },
              label = { Text("Profile", fontWeight = FontWeight.Bold) },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SomaliBluePrimary,
                selectedTextColor = SomaliBluePrimary,
                indicatorColor = Color(0xFFE3F2FD)
              ),
              modifier = Modifier.testTag("nav_profile")
            )
          }
        }
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        when (uiState.currentScreen) {
          AppScreen.AUTH -> AuthScreen(viewModel = viewModel, uiState = uiState)
          AppScreen.DASHBOARD -> DashboardScreen(viewModel = viewModel, uiState = uiState)
          AppScreen.QUIZ -> QuizScreen(viewModel = viewModel, uiState = uiState)
          AppScreen.ROOMS -> RoomsScreen(viewModel = viewModel, uiState = uiState)
          AppScreen.MULTIPLAYER_BATTLE -> MultiplayerBattleScreen(viewModel = viewModel, uiState = uiState)
          AppScreen.CHAT -> ChatScreen(viewModel = viewModel, uiState = uiState)
          AppScreen.SETTINGS -> ProfileSettingsScreen(viewModel = viewModel, uiState = uiState)
        }
      }
    }
  }
}
