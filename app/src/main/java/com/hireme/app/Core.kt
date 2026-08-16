package com.hireme.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

val DeepBlue = Color(0xFF071B3A)
val DeepBlue2 = Color(0xFF0D2D5B)
val DeepBlue3 = Color(0xFF123A70)
val Sky = Color(0xFF65C7FF)
val White = Color.White
val Muted = White.copy(alpha = .66f)
val Faint = White.copy(alpha = .42f)
val Glass = White.copy(alpha = .075f)
val Border = White.copy(alpha = .14f)

enum class Tab(val label: String, val icon: ImageVector) { HOME("Home", Icons.Outlined.Home), PROJECTS("Projects", Icons.Outlined.FolderOpen), SERVICES("Services", Icons.Outlined.GridView), MESSAGES("Messages", Icons.Outlined.ChatBubbleOutline), PROFILE("Profile", Icons.Outlined.PersonOutline) }
data class Service(val title: String, val description: String, val icon: ImageVector, val tag: String)
data class Project(val title: String, val service: String, val progress: Int, val deadline: String, val done: Boolean = false)

val services = listOf(
    Service("Video Editing", "Turn raw footage into engaging content.", Icons.Outlined.Movie, "Creative"), Service("Graphic Design", "Visuals that make your brand stand out.", Icons.Outlined.Brush, "Creative"), Service("Management", "Plan, organize and manage your projects.", Icons.Outlined.Assignment, "Operations"), Service("App Development", "Build powerful mobile applications.", Icons.Outlined.PhoneAndroid, "Technology"), Service("Integration", "Connect APIs, payments, AI and services.", Icons.Outlined.Hub, "Technology"), Service("Web Development", "Modern websites and web applications.", Icons.Outlined.Language, "Technology"))
val projects = listOf(Project("E-Commerce Website", "Web Development", 82, "Aug 22"), Project("YouTube Channel Package", "Video Editing · Graphic Design", 100, "Completed", true), Project("Creator Dashboard", "App Development", 38, "Sep 04"))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); window.statusBarColor = android.graphics.Color.rgb(7, 27, 58); window.navigationBarColor = android.graphics.Color.rgb(7, 27, 58); setContent { HireMeTheme { HireMeApp() } } }
}

@Composable fun HireMeTheme(content: @Composable () -> Unit) { MaterialTheme(colorScheme = darkColorScheme(primary = Sky, onPrimary = DeepBlue, secondary = Sky, onSecondary = DeepBlue, background = DeepBlue, onBackground = White, surface = DeepBlue2, onSurface = White, surfaceVariant = DeepBlue3, onSurfaceVariant = Muted, outline = Border), typography = Typography(defaultFontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif), content = content) }

@Composable fun HireMeApp() {
    val context = LocalContext.current; var splash by remember { mutableStateOf(true) }; var onboarding by remember { mutableStateOf(!context.getSharedPreferences("hire_me", Context.MODE_PRIVATE).getBoolean("onboarded", false)) }; var tab by remember { mutableStateOf(Tab.HOME) }; var request by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(1150); splash = false }
    Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(DeepBlue, Color(0xFF0A244A), DeepBlue)))) { Atmosphere(); when { splash -> SplashScreen(); onboarding -> OnboardingScreen { context.getSharedPreferences("hire_me", Context.MODE_PRIVATE).edit().putBoolean("onboarded", true).apply(); onboarding = false }; else -> MainShell(tab, { tab = it }) { request = true } }; if (request) RequestSheet { request = false } }
}

@Composable private fun Atmosphere() { Canvas(Modifier.fillMaxSize().alpha(.55f)) { drawCircle(Sky.copy(alpha = .07f), size.minDimension * .42f, Offset(size.width * .88f, size.height * .12f)); drawCircle(Sky.copy(alpha = .035f), size.minDimension * .62f, Offset(size.width * .08f, size.height * .72f)); drawCircle(White.copy(alpha = .04f), 2f, Offset(size.width * .18f, size.height * .2f)); drawCircle(White.copy(alpha = .04f), 1.5f, Offset(size.width * .76f, size.height * .32f)) } }

@Composable fun LogoMark(size: Dp, showWord: Boolean = false) { Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) { Canvas(Modifier.size(size)) { val w = this.size.width; val h = this.size.height; drawRoundRect(Sky.copy(alpha = .12f), Offset(w * .04f, h * .04f), androidx.compose.ui.geometry.Size(w * .92f, h * .92f), androidx.compose.ui.geometry.CornerRadius(w * .25f)); drawLine(Sky, Offset(w * .25f, h * .24f), Offset(w * .25f, h * .76f), w * .09f, StrokeCap.Round); drawLine(Sky, Offset(w * .25f, h * .5f), Offset(w * .56f, h * .5f), w * .09f, StrokeCap.Round); drawLine(White, Offset(w * .56f, h * .5f), Offset(w * .76f, h * .24f), w * .09f, StrokeCap.Round); drawLine(White, Offset(w * .56f, h * .5f), Offset(w * .76f, h * .76f), w * .09f, StrokeCap.Round); drawCircle(White, w * .065f, Offset(w * .56f, h * .5f)) }; if (showWord) Column { Text("HIRE ME", fontSize = 17.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp); Text("SERVICES, SIMPLIFIED", fontSize = 8.sp, letterSpacing = 1.7.sp, color = Sky) } } }

@Composable private fun SplashScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Box(contentAlignment = Alignment.Center) { Canvas(Modifier.size(180.dp).alpha(.65f)) { drawCircle(Sky.copy(alpha = .11f), size.minDimension * .48f); drawCircle(Sky.copy(alpha = .08f), size.minDimension * .35f) }; LogoMark(74.dp) }; Spacer(Modifier.height(20.dp)); Text("HIRE ME", fontSize = 28.sp, fontWeight = FontWeight.Bold, letterSpacing = 4.sp); Spacer(Modifier.height(8.dp)); Text("Turn ideas into reality.", color = Muted, fontSize = 14.sp) } } }

@Composable private fun OnboardingScreen(onFinish: () -> Unit) { var page by remember { mutableStateOf(0) }; val titles = listOf("Need something built?", "From idea to delivery", "One place. Every project."); val subtitles = listOf("Tell us what you need and get a professional solution.", "Manage your entire project from one place.", "Communicate, track progress, review work and stay organized."); Box(Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 28.dp)) { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { LogoMark(34.dp, true); TextButton(onClick = onFinish) { Text("SKIP", color = Muted) } }; Column(Modifier.align(Alignment.Center).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) { AnimatedContent(page, transitionSpec = { fadeIn() togetherWith fadeOut() }, label = "onboarding") { OnboardingVisual(it) }; Spacer(Modifier.height(46.dp)); Text(titles[page], fontSize = 29.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center); Spacer(Modifier.height(12.dp)); Text(subtitles[page], color = Muted, textAlign = TextAlign.Center, fontSize = 15.sp, lineHeight = 23.sp, modifier = Modifier.fillMaxWidth(.82f)) }; Row(Modifier.align(Alignment.BottomCenter).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) { repeat(3) { i -> Box(Modifier.height(5.dp).width(if (i == page) 24.dp else 7.dp).clip(CircleShape).background(if (i == page) Sky else White.copy(alpha = .2f))) } }; Button(onClick = { if (page == 2) onFinish() else page++ }, shape = RoundedCornerShape(16.dp), modifier = Modifier.height(54.dp)) { Text(if (page == 2) "GET STARTED" else "CONTINUE", fontWeight = FontWeight.Bold); Spacer(Modifier.width(8.dp)); Icon(Icons.Outlined.ArrowForward, null) } } } }

@Composable private fun OnboardingVisual(page: Int) { Box(Modifier.size(270.dp).clip(RoundedCornerShape(36.dp)).background(Brush.radialGradient(listOf(DeepBlue3.copy(alpha = .8f), DeepBlue.copy(alpha = .3f)))).border(1.dp, Border, RoundedCornerShape(36.dp)), contentAlignment = Alignment.Center) { when (page) { 0 -> Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) { listOf(Icons.Outlined.Movie, Icons.Outlined.Brush, Icons.Outlined.PhoneAndroid).forEach { MiniGlassIcon(it, 76.dp) } }; 1 -> Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) { listOf("IDEA", "PLAN", "BUILD", "DELIVER").forEachIndexed { i, label -> Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(30.dp).clip(CircleShape).background(if (i < 3) Sky else White.copy(alpha = .12f)), contentAlignment = Alignment.Center) { if (i < 3) Icon(Icons.Outlined.Check, null, tint = DeepBlue, modifier = Modifier.size(16.dp)) else Text("4") }; Spacer(Modifier.width(12.dp)); Text(label, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp) } } }; else -> Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) { Text("PROJECT HEALTH", color = Muted, fontSize = 10.sp, letterSpacing = 1.5.sp); Text("68%", fontSize = 40.sp, fontWeight = FontWeight.Bold); ProgressLine(68); Text("Development in progress", color = Sky, fontSize = 12.sp); Text("✓  Requirements approved", color = Muted, fontSize = 11.sp); Text("✓  Design completed", color = Muted, fontSize = 11.sp); Text("○  Testing next", color = Muted, fontSize = 11.sp) } } } }

@Composable private fun MiniGlassIcon(icon: ImageVector, size: Dp) { Box(Modifier.size(size).clip(RoundedCornerShape(19.dp)).background(White.copy(alpha = .08f)).border(1.dp, Border, RoundedCornerShape(19.dp)), contentAlignment = Alignment.Center) { Icon(icon, null, tint = Sky, modifier = Modifier.size(27.dp)) } }
