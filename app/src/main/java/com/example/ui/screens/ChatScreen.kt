package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppScreen
import com.example.data.AppUiState
import com.example.data.AppViewModel
import com.example.model.ChatMessage
import com.example.ui.components.SomaliStudentAvatar
import com.example.ui.components.WinnerBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
  viewModel: AppViewModel,
  uiState: AppUiState,
  modifier: Modifier = Modifier
) {
  var messageInput by remember { mutableStateOf("") }
  var selectedTag by remember { mutableStateOf("All") }
  val listState = rememberLazyListState()

  val tags = listOf("All", "Biology", "Physics", "Math", "Chemistry", "Imtixaanka Qaranka")

  val filteredMessages = if (selectedTag == "All") {
    uiState.chatMessages
  } else {
    uiState.chatMessages.filter { it.subjectTag?.equals(selectedTag, ignoreCase = true) == true }
  }

  LaunchedEffect(filteredMessages.size) {
    if (filteredMessages.isNotEmpty()) {
      listState.animateScrollToItem(filteredMessages.size - 1)
    }
  }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = SomaliSoftBg,
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "GENERAL STUDY CHAT",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = SomaliBluePrimary
              )
            )
            Text(
              text = "Wadahadalka & Is-caawinta Ardayda Fasalka 4-aad",
              style = MaterialTheme.typography.bodySmall.copy(color = SomaliTextSubtle)
            )
          }
        },
        actions = {
          IconButton(onClick = { viewModel.navigateTo(AppScreen.SETTINGS) }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = SomaliBluePrimary)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
      )
    },
    bottomBar = {
      // Bottom Chat Input Bar matching mockup
      Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
          // Quick prompt chips
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
          ) {
            val suggestions = listOf("Su'aasha maanta?", "Yaa u socda Room 1?", "Sidee loo xalliyaa Newton's Laws?", "Mendel's Laws yaa yaqaan?")
            items(suggestions) { suggestion ->
              SuggestionChip(
                onClick = { messageInput = suggestion },
                label = { Text(suggestion, fontSize = 11.sp) },
                shape = RoundedCornerShape(12.dp)
              )
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedTextField(
              value = messageInput,
              onValueChange = { messageInput = it },
              placeholder = { Text("Qor fariintaada ama su'aashaada...") },
              shape = RoundedCornerShape(24.dp),
              singleLine = true,
              colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF9FAFC),
                focusedContainerColor = Color.White
              ),
              modifier = Modifier
                .weight(1f)
                .testTag("chat_input_field")
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilledIconButton(
              onClick = {
                if (messageInput.isNotBlank()) {
                  viewModel.sendChatMessage(
                    text = messageInput,
                    subjectTag = if (selectedTag == "All") "General" else selectedTag
                  )
                  messageInput = ""
                }
              },
              colors = IconButtonDefaults.filledIconButtonColors(containerColor = SomaliBluePrimary),
              modifier = Modifier.size(48.dp).testTag("chat_send_button")
            ) {
              Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
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
    ) {
      // Subject Filter Bar
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        items(tags) { tag ->
          FilterChip(
            selected = selectedTag == tag,
            onClick = { selectedTag = tag },
            label = { Text(tag, fontSize = 12.sp, fontWeight = if (selectedTag == tag) FontWeight.Bold else FontWeight.Normal) }
          )
        }
      }

      HorizontalDivider(color = SomaliBorder)

      // Messages List
      LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(filteredMessages) { msg ->
          ChatBubbleItem(message = msg)
        }
      }
    }
  }
}

@Composable
fun ChatBubbleItem(message: ChatMessage) {
  val isMe = message.isCurrentUser

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
    verticalAlignment = Alignment.Top
  ) {
    if (!isMe) {
      SomaliStudentAvatar(
        initials = message.senderName,
        backgroundColor = SomaliBluePrimary,
        size = 38.dp
      )
      Spacer(modifier = Modifier.width(8.dp))
    }

    Column(
      horizontalAlignment = if (isMe) Alignment.End else Alignment.Start,
      modifier = Modifier.widthIn(max = 280.dp)
    ) {
      // Sender Name & Winner Badge
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Text(
          text = if (isMe) "Adiga (${message.senderName})" else message.senderName,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = if (isMe) SomaliBluePrimary else SomaliTextDark
          )
        )

        message.badge?.let { badgeText ->
          WinnerBadge(
            text = badgeText,
            isGold = badgeText.contains("Gold", ignoreCase = true) || badgeText.contains("👑")
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Bubble
      Surface(
        shape = RoundedCornerShape(
          topStart = 16.dp,
          topEnd = 16.dp,
          bottomStart = if (isMe) 16.dp else 4.dp,
          bottomEnd = if (isMe) 4.dp else 16.dp
        ),
        color = if (isMe) SomaliBluePrimary else Color.White,
        border = if (isMe) null else androidx.compose.foundation.BorderStroke(1.dp, SomaliBorder),
        shadowElevation = 1.dp
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text(
            text = message.text,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = if (isMe) Color.White else MaterialTheme.colorScheme.onSurface,
              lineHeight = 20.sp
            )
          )

          Spacer(modifier = Modifier.height(4.dp))

          Row(
            modifier = Modifier.align(Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            message.subjectTag?.let {
              Text(
                text = "#$it",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  color = if (isMe) SomaliSkyBlue else SomaliSuccessGreen
                )
              )
            }
            Text(
              text = message.timestamp,
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = if (isMe) Color.White.copy(alpha = 0.7f) else Color.Gray
              )
            )
          }
        }
      }
    }

    if (isMe) {
      Spacer(modifier = Modifier.width(8.dp))
      SomaliStudentAvatar(
        initials = message.senderName,
        backgroundColor = SomaliSuccessGreen,
        size = 38.dp
      )
    }
  }
}
