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
import com.example.data.AppScreen
import com.example.data.AppUiState
import com.example.data.AppViewModel
import com.example.ui.components.SomaliStudentAvatar
import com.example.ui.components.WinnerBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
  viewModel: AppViewModel,
  uiState: AppUiState,
  modifier: Modifier = Modifier
) {
  var isEditingProfile by remember { mutableStateOf(false) }
  var nameState by remember(uiState.userProfile.fullName) { mutableStateOf(uiState.userProfile.fullName) }
  var usernameState by remember(uiState.userProfile.username) { mutableStateOf(uiState.userProfile.username) }
  var bioState by remember(uiState.userProfile.bio) { mutableStateOf(uiState.userProfile.bio) }
  var schoolState by remember(uiState.userProfile.school) { mutableStateOf(uiState.userProfile.school) }
  var cityState by remember(uiState.userProfile.city) { mutableStateOf(uiState.userProfile.city) }

  var notificationsEnabled by remember { mutableStateOf(true) }
  var soundEffectsEnabled by remember { mutableStateOf(true) }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = SomaliSoftBg,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "PROFILE & SETTINGS",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Black,
              letterSpacing = 1.sp,
              color = SomaliBluePrimary
            )
          )
        },
        navigationIcon = {
          IconButton(onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SomaliBluePrimary)
          }
        },
        actions = {
          IconButton(onClick = {
            if (isEditingProfile) {
              viewModel.updateProfile(
                fullName = nameState,
                username = usernameState,
                bio = bioState,
                school = schoolState,
                city = cityState
              )
            }
            isEditingProfile = !isEditingProfile
          }) {
            Icon(
              imageVector = if (isEditingProfile) Icons.Default.Save else Icons.Default.Edit,
              contentDescription = if (isEditingProfile) "Save" else "Edit Profile",
              tint = SomaliBluePrimary
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
    ) {
      // Profile Cover Photo (Sawirka Daboolka) matching requirement
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.somali_school_cover),
          contentDescription = "Profile Cover Photo",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Overlay with status
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color.Black.copy(alpha = 0.5f),
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(12.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Cover Photo", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
          }
        }
      }

      // Profile Content Card Overlapping Cover
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Avatar overlapping the cover photo
        Box(
          modifier = Modifier
            .offset(y = (-45).dp)
            .size(90.dp)
            .clip(CircleShape)
            .border(4.dp, Color.White, CircleShape)
            .background(SomaliBluePrimary),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = uiState.userProfile.fullName.take(2).uppercase(),
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Black,
              color = Color.White
            )
          )
        }

        Spacer(modifier = Modifier.height((-35).dp))

        // Student Info
        Text(
          text = uiState.userProfile.fullName,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Text(
          text = "@${uiState.userProfile.username}",
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = SomaliSkyBlue
          )
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Winner Badge
        WinnerBadge(
          text = uiState.userProfile.winnerBadgeTitle,
          isGold = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Biography Card
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "TAARIIKH NCOLOLEED (BIOGRAPHY)",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Black,
                  color = SomaliBluePrimary,
                  letterSpacing = 1.sp
                )
              )
              if (!isEditingProfile) {
                TextButton(onClick = { isEditingProfile = true }) {
                  Text("Wax ka beddel", fontSize = 12.sp, color = SomaliBluePrimary)
                }
              }
            }

            if (isEditingProfile) {
              OutlinedTextField(
                value = bioState,
                onValueChange = { bioState = it },
                label = { Text("Qor Biography gaaban") },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.height(8.dp))
              OutlinedTextField(
                value = schoolState,
                onValueChange = { schoolState = it },
                label = { Text("Dugsiga Sare (School Name)") },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.height(8.dp))
              OutlinedTextField(
                value = cityState,
                onValueChange = { cityState = it },
                label = { Text("Magaalada / Gobolka (City / Region)") },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.height(8.dp))
              Button(
                onClick = {
                  viewModel.updateProfile(
                    fullName = nameState,
                    username = usernameState,
                    bio = bioState,
                    school = schoolState,
                    city = cityState
                  )
                  isEditingProfile = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = SomaliSuccessGreen),
                modifier = Modifier.align(Alignment.End)
              ) {
                Text("Keydi Isbeddelada")
              }
            } else {
              Text(
                text = uiState.userProfile.bio,
                style = MaterialTheme.typography.bodyMedium.copy(
                  lineHeight = 22.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
              Spacer(modifier = Modifier.height(8.dp))
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.School, contentDescription = null, tint = SomaliBlueLight, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "${uiState.userProfile.school} • ${uiState.userProfile.city}",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = SomaliTextSubtle
                  )
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Settings Section
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "SETTINGS (DEJINTA GUUD)",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                color = SomaliBluePrimary,
                letterSpacing = 1.sp
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text("Ogeysiisyada Imtixaannada (Notifications)", fontWeight = FontWeight.SemiBold)
                Text("Xusuusinta casharrada iyo tartamada", style = MaterialTheme.typography.bodySmall, color = SomaliTextSubtle)
              }
              Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
              )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = SomaliBorder)

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text("Dhawaaqa Tartanka (Sound Effects)", fontWeight = FontWeight.SemiBold)
                Text("Dhawaaqa countdown-ka iyo guusha", style = MaterialTheme.typography.bodySmall, color = SomaliTextSubtle)
              }
              Switch(
                checked = soundEffectsEnabled,
                onCheckedChange = { soundEffectsEnabled = it }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ABOUT APP TRIBUTE CARD (Prominently mentions Shuceyb Ahmed Mohamed as creator)
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F6FD)),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, SomaliSkyBlue),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = SomaliBluePrimary
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "ABOUT APP (KU SAABSAN APP-KA)",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Black,
                  color = SomaliBluePrimary
                )
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "Form Four Assistant Somali Students",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color.White,
              border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(
                  text = "Hal-abuuraha & Milkiilaha Mashruuca:",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = SomaliTextSubtle
                  )
                )
                Text(
                  text = "Shuceyb Ahmed Mohamed",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = SomaliBluePrimary
                  )
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "App-kan waxaa si gaar ah loogu naqshadeeyay ardayda dugsiyada sare ee Soomaaliyeed, gaar ahaan ardayda u fariisaneysa Imtixaanka Shahaadiga ah ee Fasalka 4-aad (Form Four National Examinations). Wuxuu isku xirayaa PDF Quiz Engine otomaatig ah, Qolalka Tartanka Tooska ah ee 15-ka arday, Dashboard-ka Falanqaynta Horumarka, iyo Sheekada Guud ee Ardayda.",
              style = MaterialTheme.typography.bodySmall.copy(
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "Version 1.0.0 • Made with ❤️ for Somali Education",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = SomaliTextSubtle
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Log out button
        OutlinedButton(
          onClick = { viewModel.logout() },
          colors = ButtonDefaults.outlinedButtonColors(contentColor = SomaliErrorRed),
          border = androidx.compose.foundation.BorderStroke(1.dp, SomaliErrorRed),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("logout_button")
        ) {
          Icon(Icons.Default.ExitToApp, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Ka Bax (Log Out)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(40.dp))
      }
    }
  }
}
