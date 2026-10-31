package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppScreen
import com.example.data.AppViewModel
import com.example.data.SampleData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun testAppNameResource() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Form Four Assistant", appName)
  }

  @Test
  fun testAppViewModelInitialState() {
    val viewModel = AppViewModel()
    val state = viewModel.uiState.value

    assertEquals("Abdikadir Nur", state.userProfile.fullName)
    assertEquals("abdikadir_so", state.userProfile.username)
    assertTrue(state.pdfs.isNotEmpty())
    assertTrue(state.rooms.isNotEmpty())
    assertTrue(state.chatMessages.isNotEmpty())
  }

  @Test
  fun testQuizFlow() {
    val viewModel = AppViewModel()
    val pdf = SampleData.samplePdfs.first()

    viewModel.startQuiz(pdf)
    assertEquals(AppScreen.QUIZ, viewModel.uiState.value.currentScreen)
    assertNotNull(viewModel.uiState.value.quizState)

    // Select correct answer for bio_q1: "DNA (Deoxyribonucleic acid)"
    viewModel.selectQuizAnswer("DNA (Deoxyribonucleic acid)")
    viewModel.submitAnswer()

    val quizState = viewModel.uiState.value.quizState
    assertNotNull(quizState)
    assertTrue(quizState!!.isAnswerSubmitted)
    assertTrue(quizState.isAnswerCorrect)
    assertEquals(10, quizState.score)
  }

  @Test
  fun testCreateStudyRoom() {
    val viewModel = AppViewModel()
    val initialCount = viewModel.uiState.value.rooms.size

    viewModel.createRoom(
      name = "Somali Physics Club",
      subject = "Physics",
      password = "",
      maxPlayers = 15
    )

    val updatedCount = viewModel.uiState.value.rooms.size
    assertEquals(initialCount + 1, updatedCount)
    assertEquals("Somali Physics Club", viewModel.uiState.value.rooms.first().name)
  }

  @Test
  fun testSendChatMessage() {
    val viewModel = AppViewModel()
    val initialMsgCount = viewModel.uiState.value.chatMessages.size

    viewModel.sendChatMessage("Maanta waxaan baranay Genetics!", "Biology")

    val updatedMsgCount = viewModel.uiState.value.chatMessages.size
    assertEquals(initialMsgCount + 1, updatedMsgCount)
    assertEquals("Maanta waxaan baranay Genetics!", viewModel.uiState.value.chatMessages.last().text)
  }
}
