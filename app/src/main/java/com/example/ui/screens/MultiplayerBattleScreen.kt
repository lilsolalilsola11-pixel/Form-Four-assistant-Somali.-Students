package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.data.SampleData
import com.example.model.BattleParticipant
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiplayerBattleScreen(
  viewModel: AppViewModel,
  uiState: AppUiState,
  modifier: Modifier = Modifier
) {
  val currentBattleQ = SampleData.biologyQuestions.getOrNull(uiState.battleQuestionIndex)
  var hasAnsweredCurrentQ by remember(uiState.battleQuestionIndex) { mutableStateOf(false) }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = SomaliSoftBg,
    topBar = {
      TopAppBar(
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.SportsEsports, contentDescription = null, tint = SomaliSuccessGreen)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "15-PLAYER BATTLE ARENA",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                color = SomaliBluePrimary
              )
            )
          }
        },
        navigationIcon = {
          IconButton(onClick = { viewModel.navigateTo(AppScreen.ROOMS) }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SomaliBluePrimary)
          }
        },
        actions = {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (uiState.battleTimeRemaining <= 3) Color(0xFFFFEBEE) else Color(0xFFE8F5E9),
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (uiState.battleTimeRemaining <= 3) SomaliErrorRed else SomaliBrightGreen
            )
          ) {
            Text(
              text = "${uiState.battleTimeRemaining}s",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                color = if (uiState.battleTimeRemaining <= 3) SomaliErrorRed else SomaliSuccessGreen
              ),
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp)
    ) {
      if (!uiState.battleCompleted) {
        // Round Indicator
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Dagaalka Su'aalaha • Wareegga ${uiState.battleQuestionIndex + 1} / 4",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = SomaliBluePrimary
            )
          )
          Text(
            text = "Dhibcahaaga: ${uiState.battleScore} pts",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = SomaliSuccessGreen
            )
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Battle Question Card
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, SomaliSkyBlue),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = currentBattleQ?.prompt ?: "What is the unit of Force?",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            currentBattleQ?.somaliPrompt?.let {
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = it,
                style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle)
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Options (Options A, B, C, D)
            val options = currentBattleQ?.options?.take(4) ?: listOf("Option A", "Option B", "Option C", "Option D")
            options.forEach { opt ->
              OutlinedButton(
                onClick = {
                  if (!hasAnsweredCurrentQ) {
                    hasAnsweredCurrentQ = true
                    val isCorrect = opt == currentBattleQ?.correctAnswer
                    viewModel.submitBattleAnswer(isCorrect)
                  }
                },
                enabled = !hasAnsweredCurrentQ,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                  containerColor = if (hasAnsweredCurrentQ && opt == currentBattleQ?.correctAnswer) SomaliGreenContainer else Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (hasAnsweredCurrentQ && opt == currentBattleQ?.correctAnswer) SomaliSuccessGreen else SomaliBorder
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 3.dp)
                  .testTag("battle_opt_${opt.take(4)}")
              ) {
                Text(
                  text = opt,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = if (hasAnsweredCurrentQ && opt == currentBattleQ?.correctAnswer) SomaliSuccessGreen else MaterialTheme.colorScheme.onSurface
                  ),
                  textAlign = TextAlign.Start,
                  modifier = Modifier.fillMaxWidth()
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Live 15 Students Real-time Leaderboard
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "TARTANKA TOOSKA AH (15 ARDAY)",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Black,
              color = SomaliBluePrimary,
              letterSpacing = 1.sp
            )
          )
          Text(
            text = "Toos (Live Updating)",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = SomaliBrightGreen
            )
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.weight(1f)
        ) {
          itemsIndexed(uiState.battlePeers) { index, peer ->
            BattlePeerRow(rank = index + 1, peer = peer)
          }
        }
      } else {
        // Battle Completed / Winner Ceremony
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(2.dp, SomaliGoldCrown),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(text = "👑🥇", fontSize = 54.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "TARTANKII 15-KA ARDAY WAA DHAMMAADAY!",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                color = SomaliBluePrimary
              ),
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
              shape = RoundedCornerShape(16.dp),
              color = SomaliGoldContainer,
              border = androidx.compose.foundation.BorderStroke(1.dp, SomaliGoldCrown),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text(
                  text = "GUULEYSTAHA KAALINTA 1-AAD:",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF8C5000)
                  )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = uiState.battleWinner?.name ?: "Abdikadir Nur",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF8C5000)
                  )
                )
                Text(
                  text = "${uiState.battleWinner?.score ?: 45} dhibcood • ${uiState.battleWinner?.school ?: "21 Oktoobar Sec"}",
                  style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle)
                )
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Winner Perks explanation matching user brief
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F6FD)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Verified, contentDescription = null, tint = SomaliGoldCrown)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "Calaamadda '👑 Gold Champion' waxay ka muuqanaysaa magacaaga agtiisa Qolalka iyo Sheekada Guud ee Ardayda!",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = SomaliBluePrimary
                  )
                )
              }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              OutlinedButton(
                onClick = { viewModel.navigateTo(AppScreen.CHAT) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(48.dp)
              ) {
                Text("Sheekada Guud", fontWeight = FontWeight.Bold)
              }

              Button(
                onClick = { viewModel.navigateTo(AppScreen.ROOMS) },
                colors = ButtonDefaults.buttonColors(containerColor = SomaliSuccessGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(48.dp)
              ) {
                Text("Ku Noqo Qolalka", fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun BattlePeerRow(
  rank: Int,
  peer: BattleParticipant
) {
  val isTopRank = rank == 1
  val isUser = peer.isUser

  Surface(
    shape = RoundedCornerShape(10.dp),
    color = when {
      isUser -> Color(0xFFE3F2FD)
      isTopRank -> SomaliGoldContainer
      else -> Color.White
    },
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      when {
        isUser -> SomaliBluePrimary
        isTopRank -> SomaliGoldCrown
        else -> SomaliBorder
      }
    ),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (isTopRank) "👑 #$rank" else "#$rank",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          color = if (isTopRank) SomaliGoldCrown else SomaliTextSubtle
        ),
        modifier = Modifier.width(42.dp)
      )

      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(Color(peer.avatarColor)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = peer.name.take(1),
          color = Color.White,
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp
        )
      }

      Spacer(modifier = Modifier.width(8.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = peer.name,
          style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = if (isUser || isTopRank) FontWeight.Bold else FontWeight.Medium,
            color = if (isUser) SomaliBluePrimary else MaterialTheme.colorScheme.onSurface
          ),
          maxLines = 1
        )
      }

      Text(
        text = "${peer.score} pts",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          color = if (isTopRank) SomaliGoldCrown else SomaliBluePrimary
        )
      )
    }
  }
}
