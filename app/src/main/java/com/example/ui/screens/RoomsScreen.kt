package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.data.*
import com.example.model.LeaderboardUser
import com.example.model.StudyRoom
import com.example.ui.components.SomaliStudentAvatar
import com.example.ui.components.WinnerBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomsScreen(
  viewModel: AppViewModel,
  uiState: AppUiState,
  modifier: Modifier = Modifier
) {
  var roomNameInput by remember { mutableStateOf("Biology Study Group") }
  var roomPasswordInput by remember { mutableStateOf("") }
  var maxPlayersInput by remember { mutableStateOf(15) }
  var roomSubjectInput by remember { mutableStateOf("Biology") }

  val activeLeaderboard = if (uiState.leaderboardTab == LeaderboardTab.HIGHEST_SCORE) {
    SampleData.scoreLeaderboard
  } else {
    SampleData.streakLeaderboard
  }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = SomaliSoftBg,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "COMPETE WITH FRIENDS",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Black,
              letterSpacing = 1.sp,
              color = SomaliBluePrimary
            )
          )
        },
        actions = {
          IconButton(onClick = { viewModel.openCreateRoomDialog(true) }) {
            Icon(Icons.Default.AddHome, contentDescription = "Create Room", tint = SomaliBluePrimary)
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
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Toggle Tabs: HIGHEST SCORE | BEST STREAK
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
          TabButton(
            title = "HIGHEST SCORE",
            isSelected = uiState.leaderboardTab == LeaderboardTab.HIGHEST_SCORE,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.setLeaderboardTab(LeaderboardTab.HIGHEST_SCORE) }
          )
          TabButton(
            title = "BEST STREAK",
            isSelected = uiState.leaderboardTab == LeaderboardTab.BEST_STREAK,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.setLeaderboardTab(LeaderboardTab.BEST_STREAK) }
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Ranking Podium (#1 Gold, #2 Silver, #3 Bronze)
      RankingPodiumCard(
        topThree = activeLeaderboard.take(3),
        isStreakMode = uiState.leaderboardTab == LeaderboardTab.BEST_STREAK
      )

      Spacer(modifier = Modifier.height(18.dp))

      // CREATE ROOM Button matching mockup
      Button(
        onClick = { viewModel.openCreateRoomDialog(true) },
        colors = ButtonDefaults.buttonColors(containerColor = SomaliSuccessGreen),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("create_room_banner_btn")
      ) {
        Icon(Icons.Default.GroupAdd, contentDescription = null, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "CREATE ROOM (Qol samee)",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // OPEN ROOMS / AUTOMATIC JOIN Section
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "OPEN ROOMS / AUTOMATIC JOIN",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Black,
              color = SomaliBluePrimary
            )
          )
          Text(
            text = "Qolalka furan / Ku biirid toos ah",
            style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Rooms List
      Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        uiState.rooms.forEachIndexed { index, room ->
          StudyRoomCard(
            index = index + 1,
            room = room,
            onJoin = { viewModel.joinRoom(room) },
            onInvite = { viewModel.openInviteDialog(true, room.name) }
          )
        }
      }

      Spacer(modifier = Modifier.height(22.dp))

      // 15-PLAYER BATTLE Banner matching mockup
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, SomaliBrightGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(Icons.Default.Bolt, contentDescription = null, tint = SomaliSuccessGreen)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "15-PLAYER BATTLE",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                color = SomaliSuccessGreen
              )
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "Countdown",
            style = MaterialTheme.typography.labelMedium.copy(color = SomaliTextSubtle)
          )

          Text(
            text = uiState.battleCountdownFormatted,
            style = MaterialTheme.typography.headlineLarge.copy(
              fontWeight = FontWeight.Black,
              color = SomaliTextDark,
              letterSpacing = 2.sp
            )
          )

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { viewModel.startMultiplayerBattle() },
            colors = ButtonDefaults.buttonColors(containerColor = SomaliSuccessGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("join_battle_button")
          ) {
            Icon(Icons.Default.SportsEsports, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "JOIN BATTLE (Ku biir Tartanka)",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Real-time multiplayer competition with 15 Somali students.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = SomaliTextSubtle,
              textAlign = TextAlign.Center
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(40.dp))
    }
  }

  // Create Room Dialog matching mockup
  if (uiState.showCreateRoomDialog) {
    AlertDialog(
      onDismissRequest = { viewModel.openCreateRoomDialog(false) },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.MeetingRoom, contentDescription = null, tint = SomaliBluePrimary)
          Spacer(modifier = Modifier.width(8.dp))
          Text("CREATE ROOM (Qol Samee)", fontWeight = FontWeight.Bold)
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "QOLKA MAGACIISA / ROOM NAME",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
          )
          OutlinedTextField(
            value = roomNameInput,
            onValueChange = { roomNameInput = it },
            placeholder = { Text("tusaale: Biology Study Group") },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("create_room_name_input")
          )

          Text(
            text = "PASSWORD (IKHTIYAAR - OPTIONAL)",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
          )
          OutlinedTextField(
            value = roomPasswordInput,
            onValueChange = { roomPasswordInput = it },
            placeholder = { Text("Furaha haddii aad rabto qol gaar ah") },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("create_room_password_input")
          )

          Text(
            text = "DARAASADAHA MAX: [15 ARDAY]",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = SomaliBluePrimary
            )
          )
          Text(
            text = "Tartanka qolka wuxuu qaadayaa ilaa 15 arday hal mar isla waqtigaas.",
            style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.createRoom(
              name = roomNameInput,
              subject = roomSubjectInput,
              password = roomPasswordInput,
              maxPlayers = 15
            )
          },
          colors = ButtonDefaults.buttonColors(containerColor = SomaliSuccessGreen),
          modifier = Modifier.testTag("create_and_start_btn")
        ) {
          Text("SAMEE & BILOW (CREATE & START)")
        }
      },
      dismissButton = {
        TextButton(onClick = { viewModel.openCreateRoomDialog(false) }) {
          Text("Ka noqo")
        }
      }
    )
  }

  // Invite Friend Dialog
  if (uiState.showInviteDialog) {
    AlertDialog(
      onDismissRequest = { viewModel.openInviteDialog(false) },
      icon = { Icon(Icons.Default.Share, contentDescription = null, tint = SomaliBluePrimary) },
      title = { Text("INVITE FRIEND (Martiqaad Saaxiib)", fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(text = "La wadaag link-ga ama koodhka qolka '${uiState.inviteRoomName}':")
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF1F6FD),
            border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = "https://formfour.assistant.so/room/${uiState.inviteRoomName.hashCode().toString().takeLast(6)}",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = SomaliBluePrimary
              ),
              modifier = Modifier.padding(10.dp)
            )
          }
          Text(
            text = "Saaxiibkaagu wuxuu si toos ah ugu soo biiri karaa tartanka qolka!",
            style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = { viewModel.openInviteDialog(false) },
          colors = ButtonDefaults.buttonColors(containerColor = SomaliBluePrimary)
        ) {
          Text("Koobiyee & Dir Link-ga")
        }
      },
      dismissButton = {
        TextButton(onClick = { viewModel.openInviteDialog(false) }) {
          Text("Xir")
        }
      }
    )
  }
}

@Composable
fun TabButton(
  title: String,
  isSelected: Boolean,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(if (isSelected) SomaliBluePrimary else Color.Transparent)
      .clickable { onClick() }
      .padding(vertical = 10.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.titleSmall.copy(
        fontWeight = FontWeight.ExtraBold,
        color = if (isSelected) Color.White else SomaliTextSubtle,
        fontSize = 12.sp
      )
    )
  }
}

@Composable
fun RankingPodiumCard(
  topThree: List<LeaderboardUser>,
  isStreakMode: Boolean
) {
  val first = topThree.getOrNull(0)
  val second = topThree.getOrNull(1)
  val third = topThree.getOrNull(2)

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = if (isStreakMode) "DHALINYARADA UGU STREAK-KA SAREEYA" else "KAAWINTA GUUD EE SOOMAALIYA",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Black,
          color = SomaliTextSubtle,
          letterSpacing = 1.sp
        )
      )

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
      ) {
        // #2 Silver
        second?.let {
          PodiumColumn(
            user = it,
            rank = 2,
            badgeEmoji = "🥈",
            badgeColor = SomaliSilver,
            podiumHeight = 75.dp,
            isStreak = isStreakMode
          )
        }

        // #1 Gold (Taller, center)
        first?.let {
          PodiumColumn(
            user = it,
            rank = 1,
            badgeEmoji = "👑🥇",
            badgeColor = SomaliGoldCrown,
            podiumHeight = 100.dp,
            isFirst = true,
            isStreak = isStreakMode
          )
        }

        // #3 Bronze
        third?.let {
          PodiumColumn(
            user = it,
            rank = 3,
            badgeEmoji = "🥉",
            badgeColor = SomaliBronze,
            podiumHeight = 60.dp,
            isStreak = isStreakMode
          )
        }
      }
    }
  }
}

@Composable
fun PodiumColumn(
  user: LeaderboardUser,
  rank: Int,
  badgeEmoji: String,
  badgeColor: Color,
  podiumHeight: androidx.compose.ui.unit.Dp,
  isFirst: Boolean = false,
  isStreak: Boolean = false
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.width(95.dp)
  ) {
    Text(text = badgeEmoji, fontSize = if (isFirst) 22.sp else 18.sp)

    Box(
      modifier = Modifier
        .size(if (isFirst) 52.dp else 44.dp)
        .clip(CircleShape)
        .border(2.dp, badgeColor, CircleShape)
        .background(SomaliBluePrimary),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = user.name.take(1),
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = if (isFirst) 20.sp else 16.sp
      )
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = user.name.substringBefore(" "),
      style = MaterialTheme.typography.bodySmall.copy(
        fontWeight = if (isFirst) FontWeight.Bold else FontWeight.Medium,
        fontSize = 11.sp
      ),
      maxLines = 1,
      textAlign = TextAlign.Center
    )

    Text(
      text = if (isStreak) "${user.streak}d 🔥" else "${user.score} pts",
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.ExtraBold,
        color = if (isFirst) SomaliGoldCrown else SomaliBluePrimary,
        fontSize = 11.sp
      )
    )

    Spacer(modifier = Modifier.height(6.dp))

    // Podium Block
    Surface(
      shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
      color = if (isFirst) SomaliGoldContainer else Color(0xFFF0F4F8),
      border = androidx.compose.foundation.BorderStroke(1.dp, badgeColor),
      modifier = Modifier
        .width(70.dp)
        .height(podiumHeight)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = "#$rank",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Black,
            color = badgeColor
          )
        )
      }
    }
  }
}

@Composable
fun StudyRoomCard(
  index: Int,
  room: StudyRoom,
  onJoin: () -> Unit,
  onInvite: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
    modifier = Modifier.fillMaxWidth().testTag("room_card_${room.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(CircleShape)
          .background(SomaliBluePrimary.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.Groups, contentDescription = null, tint = SomaliBluePrimary)
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "ROOM $index",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold)
          )
          if (room.hasPassword) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color.Gray, modifier = Modifier.size(14.dp))
          }
        }
        Text(
          text = "${room.name} (${room.currentPlayers}/${room.maxPlayers} PLAYERS)",
          style = MaterialTheme.typography.bodySmall.copy(
            color = SomaliTextSubtle,
            fontSize = 11.sp
          ),
          maxLines = 1
        )
      }

      Spacer(modifier = Modifier.width(6.dp))

      OutlinedButton(
        onClick = onInvite,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = Modifier.height(34.dp)
      ) {
        Text("INVITE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SomaliBluePrimary)
      }

      Spacer(modifier = Modifier.width(6.dp))

      Button(
        onClick = onJoin,
        colors = ButtonDefaults.buttonColors(containerColor = SomaliSuccessGreen),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        modifier = Modifier.height(34.dp).testTag("join_room_btn_${room.id}")
      ) {
        Text("KU BIIR", fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}
