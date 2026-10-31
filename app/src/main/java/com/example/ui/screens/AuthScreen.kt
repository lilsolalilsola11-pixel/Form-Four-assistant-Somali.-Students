package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.AppUiState
import com.example.data.AppViewModel
import com.example.model.LeaderboardUser
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
  viewModel: AppViewModel,
  uiState: AppUiState,
  modifier: Modifier = Modifier
) {
  var isLoginMode by remember { mutableStateOf(true) }
  var nameInput by remember { mutableStateOf("Abdikadir Nur") }
  var usernameInput by remember { mutableStateOf("abdikadir_so") }
  var emailInput by remember { mutableStateOf("abdikadir@school.so") }
  var passwordInput by remember { mutableStateOf("••••••••") }
  var showFriendProfileDialog by remember { mutableStateOf<LeaderboardUser?>(null) }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = Color.White
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(8.dp))

      // App Title
      Text(
        text = "FORM FOUR ASSISTANT",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Black,
          letterSpacing = 1.sp,
          color = SomaliBluePrimary
        ),
        textAlign = TextAlign.Center
      )
      Text(
        text = "SOMALI STUDENTS",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          letterSpacing = 2.sp,
          color = SomaliSkyBlue
        ),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Hero Illustration of Somali Students
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.somali_students_hero),
          contentDescription = "Somali Form Four Students in School Uniform",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Form Fields
      OutlinedTextField(
        value = if (isLoginMode) usernameInput else nameInput,
        onValueChange = { if (isLoginMode) usernameInput = it else nameInput = it },
        label = { Text(if (isLoginMode) "Username / E-mail" else "Magacaaga oo buuxa (Full Name)") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SomaliBluePrimary) },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("username_input")
      )

      if (!isLoginMode) {
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
          value = usernameInput,
          onValueChange = { usernameInput = it },
          label = { Text("Dooro Username (tusaale: @fatima_sci)") },
          leadingIcon = { Icon(Icons.Default.AlternateEmail, contentDescription = null, tint = SomaliBluePrimary) },
          shape = RoundedCornerShape(12.dp),
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("new_username_input")
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = passwordInput,
        onValueChange = { passwordInput = it },
        label = { Text("Password (Furaha sirta)") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = SomaliBluePrimary) },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("password_input")
      )

      Spacer(modifier = Modifier.height(16.dp))

      // LOGIN Button
      Button(
        onClick = { viewModel.login(name = nameInput, username = usernameInput) },
        colors = ButtonDefaults.buttonColors(containerColor = SomaliBluePrimary),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("login_button")
      ) {
        Text(
          text = if (isLoginMode) "LOGIN" else "SIGN UP & BILOW",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // SIGN UP Switch Button
      OutlinedButton(
        onClick = { isLoginMode = !isLoginMode },
        colors = ButtonDefaults.outlinedButtonColors(
          contentColor = SomaliSuccessGreen
        ),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, SomaliSuccessGreen),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("signup_toggle_button")
      ) {
        Text(
          text = if (isLoginMode) "SIGN UP" else "HORAY AYAAD ACCOUNT U LAHAYD? LOGIN",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // OR Divider
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
          text = "OR",
          style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.Bold,
            color = Color.Gray
          ),
          modifier = Modifier.padding(horizontal = 12.dp)
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Google & Facebook Login Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F9FC)),
          border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .clickable {
              viewModel.login(name = "Fatima Omar Ali", username = "fatima_ali")
            }
            .testTag("google_login_btn")
        ) {
          Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(Icons.Default.AccountCircle, contentDescription = "Google", tint = Color(0xFFEA4335))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Google", fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }
        }

        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF1877F2)),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .clickable {
              viewModel.login(name = "Liban Mohamed Hassan", username = "liban_mh")
            }
            .testTag("facebook_login_btn")
        ) {
          Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(Icons.Default.Groups, contentDescription = "Facebook", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Facebook", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Search for username
      OutlinedTextField(
        value = uiState.friendSearchQuery,
        onValueChange = { viewModel.searchFriends(it) },
        placeholder = { Text("Q Search for username (Raadi saaxiibadaada)") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SomaliTextSubtle) },
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
          unfocusedContainerColor = Color(0xFFF9FAFC),
          focusedContainerColor = Color.White
        ),
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("search_username_field")
      )

      // Search results dropdown list if query is not empty
      if (uiState.friendSearchResults.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(8.dp)) {
            Text(
              text = "Saaxiibada la helay (${uiState.friendSearchResults.size})",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = SomaliBluePrimary
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            uiState.friendSearchResults.take(3).forEach { user ->
              ListItem(
                headlineContent = { Text(user.name, fontWeight = FontWeight.Bold) },
                supportingContent = { Text("${user.username} • ${user.school}") },
                leadingContent = {
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .clip(CircleShape)
                      .background(SomaliBluePrimary),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(user.name.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                  }
                },
                trailingContent = {
                  TextButton(onClick = { showFriendProfileDialog = user }) {
                    Text("Profile", color = SomaliSkyBlue)
                  }
                },
                modifier = Modifier.clickable { showFriendProfileDialog = user }
              )
            }
          }
        }
      }
    }
  }

  // Friend Profile Dialog preview
  showFriendProfileDialog?.let { user ->
    AlertDialog(
      onDismissRequest = { showFriendProfileDialog = null },
      icon = { Icon(Icons.Default.Badge, contentDescription = null, tint = SomaliBluePrimary) },
      title = { Text(user.name, fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(text = "Username: ${user.username}", fontWeight = FontWeight.SemiBold, color = SomaliBlueLight)
          Text(text = "Dugsiga: ${user.school}")
          Text(text = "Dhibcaha ugu sarreeya: ${user.score} pts", fontWeight = FontWeight.Bold, color = SomaliSuccessGreen)
          Text(text = "Streak-ka: ${user.streak} Maalmood 🔥")
          Text(text = "Kaalinta: #${user.rank} ee Soomaaliya")
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.login(name = user.name, username = user.username.removePrefix("@"))
            showFriendProfileDialog = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = SomaliBluePrimary)
        ) {
          Text("Ku gal Account-kan")
        }
      },
      dismissButton = {
        TextButton(onClick = { showFriendProfileDialog = null }) {
          Text("Xir")
        }
      }
    )
  }
}
