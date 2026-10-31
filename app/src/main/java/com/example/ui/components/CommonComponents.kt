package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun CircularProgressMeter(
  progress: Float,
  modifier: Modifier = Modifier,
  size: Dp = 150.dp,
  strokeWidth: Dp = 12.dp,
  primaryColor: Color = SomaliBluePrimary,
  secondaryColor: Color = SomaliBrightGreen,
  backgroundColor: Color = Color(0xFFE0E0E0),
  label: String = "TOTAL PROGRESS"
) {
  val animatedProgress by animateFloatAsState(
    targetValue = progress.coerceIn(0f, 1f),
    animationSpec = tween(durationMillis = 1000),
    label = "progress_anim"
  )

  Box(
    modifier = modifier.size(size),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      // Background Circle
      drawCircle(
        color = backgroundColor,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
      )

      // Progress Arc with Gradient
      drawArc(
        brush = Brush.sweepGradient(
          listOf(primaryColor, secondaryColor, primaryColor)
        ),
        startAngle = -90f,
        sweepAngle = animatedProgress * 360f,
        useCenter = false,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
      )
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = "${(animatedProgress * 100).toInt()}%",
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          color = SomaliTextSubtle,
          letterSpacing = 1.sp
        ),
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
fun SubjectProgressRing(
  title: String,
  percent: Int,
  color: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {}
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() }
      .padding(6.dp)
  ) {
    Box(
      modifier = Modifier.size(58.dp),
      contentAlignment = Alignment.Center
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
          color = color.copy(alpha = 0.15f),
          style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
        )
        drawArc(
          color = color,
          startAngle = -90f,
          sweepAngle = (percent / 100f) * 360f,
          useCenter = false,
          style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
        )
      }
      Text(
        text = "$percent%",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          color = color
        )
      )
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.bodySmall.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp
      ),
      maxLines = 1
    )
  }
}

@Composable
fun WinnerBadge(
  text: String,
  modifier: Modifier = Modifier,
  isGold: Boolean = true
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = if (isGold) SomaliGoldContainer else SomaliGreenContainer,
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (isGold) SomaliGoldCrown else SomaliSuccessGreen
    ),
    modifier = modifier
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Star,
        contentDescription = null,
        tint = if (isGold) SomaliGoldCrown else SomaliSuccessGreen,
        modifier = Modifier.size(14.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          color = if (isGold) Color(0xFF8C5000) else SomaliSuccessGreen
        )
      )
    }
  }
}

@Composable
fun SomaliStudentAvatar(
  initials: String,
  backgroundColor: Color = SomaliBluePrimary,
  size: Dp = 44.dp,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .size(size)
      .clip(CircleShape)
      .background(backgroundColor),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = initials.take(2).uppercase(),
      style = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
    )
  }
}
