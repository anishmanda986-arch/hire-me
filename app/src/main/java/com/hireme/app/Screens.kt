package com.hireme.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun MessagesScreen() {
    LazyMessageColumn {
        item { PageHeader("Messages", "Keep every project conversation in one place.") }
        item { GlassCard { Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Avatar("A", 48.dp); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Anish", fontWeight = FontWeight.Bold); Text("Online · replies within a few hours", color = Sky, fontSize = 12.sp) }; Icon(Icons.Outlined.ArrowForward, null, tint = Sky) } } }
        item { ChatBubble("Anish", "Hey! I’ve reviewed the latest requirements. The dashboard flow is ready for your feedback.", "10:42 AM", false) }
        item { ChatBubble("You", "Looks great. Please add dark mode to the settings screen.", "10:45 AM", true) }
        item { GlassCard { Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.CheckCircle, null, tint = Sky); Spacer(Modifier.width(10.dp)); Column(Modifier.weight(1f)) { Text("Milestone approved", fontWeight = FontWeight.Bold, fontSize = 13.sp); Text("Design phase · Aug 16", color = Muted, fontSize = 11.sp) }; Text("VIEW", color = Sky, fontSize = 11.sp, fontWeight = FontWeight.Bold) } } }
    }
}

@Composable
private fun LazyMessageColumn(content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit) {
    LazyColumn(Modifier.fillMaxSize().padding(horizontal = 20.dp), contentPadding = PaddingValues(top = 26.dp, bottom = 30.dp), verticalArrangement = Arrangement.spacedBy(14.dp), content = content)
}

@Composable
private fun ChatBubble(name: String, text: String, time: String, mine: Boolean) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = if (mine) Arrangement.End else Arrangement.Start) {
        Surface(color = if (mine) Sky.copy(alpha = .18f) else Glass, shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, if (mine) Sky.copy(alpha = .3f) else Border), modifier = Modifier.widthIn(max = 310.dp)) {
            Column(Modifier.padding(14.dp)) { Text(name, color = Sky, fontSize = 11.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(5.dp)); Text(text, color = White, fontSize = 13.sp, lineHeight = 19.sp); Spacer(Modifier.height(6.dp)); Text(time, color = Faint, fontSize = 10.sp, modifier = Modifier.align(Alignment.End)) }
        }
    }
}

@Composable
fun ProfileScreen() {
    val profileServices = listOf("App Development" to Icons.Outlined.PhoneAndroid, "Video Editing" to Icons.Outlined.Movie, "Graphic Design" to Icons.Outlined.Brush, "Web Development" to Icons.Outlined.Language)
    LazyColumn(Modifier.fillMaxSize().padding(horizontal = 20.dp), contentPadding = PaddingValues(top = 26.dp, bottom = 30.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { PageHeader("Profile", "Your professional services partner.") }
        item { GlassCard { Column(Modifier.padding(21.dp), horizontalAlignment = Alignment.CenterHorizontally) { Avatar("A", 78.dp); Spacer(Modifier.height(12.dp)); Text("Anish", fontSize = 24.sp, fontWeight = FontWeight.Bold); Text("Developer · Editor · Designer · Problem Solver", color = Muted, fontSize = 12.sp, textAlign = TextAlign.Center); Spacer(Modifier.height(10.dp)); Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(8.dp).clip(CircleShape).background(Sky)); Spacer(Modifier.width(7.dp)); Text("Available for new projects", color = Sky, fontSize = 12.sp) }; Spacer(Modifier.height(18.dp)); Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) { ProfileStat("32", "Projects"); ProfileStat("4.9", "Rating"); ProfileStat("21", "Repeat clients") } } } }
        item { SectionHeader("What I do", "") }
        items(profileServices) { (title, icon) -> CompactService(title, icon) }
        item { SectionHeader("Reviews", "See all") }
        item { ReviewCard() }
        item { SectionHeader("Settings", "") }
        item { SettingsCard() }
    }
}

@Composable
private fun CompactService(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) { Row(Modifier.fillMaxWidth().padding(vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(38.dp).clip(RoundedCornerShape(12.dp)).background(Sky.copy(alpha = .11f)), contentAlignment = Alignment.Center) { Icon(icon, null, tint = Sky, modifier = Modifier.size(19.dp)) }; Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold); Text("Professional, reliable and built around your goals.", fontSize = 11.sp, color = Muted) }; Icon(Icons.Outlined.ChevronRight, null, tint = Faint, modifier = Modifier.size(18.dp)) } }

@Composable
private fun ReviewCard() { GlassCard { Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) { Row { repeat(5) { Icon(Icons.Outlined.Star, null, tint = Sky, modifier = Modifier.size(16.dp)) }; Spacer(Modifier.weight(1f)); Text("4.9", color = Sky, fontWeight = FontWeight.Bold) }; Text("“Anish understood the brief quickly and made the whole process feel simple.”", color = White, fontSize = 13.sp, lineHeight = 19.sp); Text("Rahul · Business owner", color = Muted, fontSize = 11.sp) } }
}

@Composable
private fun SettingsCard() { GlassCard { Column { listOf(Icons.Outlined.Edit to "Edit profile", Icons.Outlined.NotificationsNone to "Notifications", Icons.Outlined.Lock to "Security & privacy", Icons.Outlined.HelpOutline to "Help & support").forEach { (icon, label) -> Row(Modifier.fillMaxWidth().clickable { }.padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) { Icon(icon, null, tint = Muted, modifier = Modifier.size(20.dp)); Spacer(Modifier.width(13.dp)); Text(label, Modifier.weight(1f), fontSize = 13.sp); Icon(Icons.Outlined.ChevronRight, null, tint = Faint, modifier = Modifier.size(17.dp)) } } } }

@Composable
fun RequestSheet(onClose: () -> Unit) {
    var stage by remember { mutableStateOf(0) }
    var requirement by remember { mutableStateOf("") }
    ModalBottomSheet(onDismissRequest = onClose, sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true), containerColor = DeepBlue2, contentColor = White, dragHandle = { Box(Modifier.padding(vertical = 10.dp).size(42.dp, 4.dp).clip(CircleShape).background(White.copy(alpha = .3f))) }) {
        when (stage) {
            0 -> Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(horizontal = 22.dp).padding(bottom = 30.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) { Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { Text("Tell me what you need", fontSize = 24.sp, fontWeight = FontWeight.Bold, Modifier.weight(1f)); IconButton(onClick = onClose) { Icon(Icons.Outlined.Close, "Close", tint = Muted) } }; Text("Describe your project naturally. I’ll turn it into a clear scope and preliminary estimate.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp); OutlinedTextField(value = requirement, onValueChange = { requirement = it }, modifier = Modifier.fillMaxWidth().height(170.dp), placeholder = { Text("I need an Android app for my business with login, payment and admin panel...", color = Faint) }, colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Border, focusedBorderColor = Sky, unfocusedTextColor = White, focusedTextColor = White, cursorColor = Sky), shape = RoundedCornerShape(18.dp)); Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(9.dp)) { Suggestion("Mobile app"); Suggestion("Website"); Suggestion("Video package") }; Button(onClick = { stage = 1 }, enabled = requirement.isNotBlank(), modifier = Modifier.fillMaxWidth().height(54.dp), shape = RoundedCornerShape(16.dp)) { Text("UNDERSTAND MY PROJECT", fontWeight = FontWeight.Bold); Spacer(Modifier.width(7.dp)); Icon(Icons.Outlined.ArrowForward, null) } }
            1 -> { LaunchedEffect(Unit) { delay(1700); stage = 2 }; Column(Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(bottom = 38.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(18.dp)) { Spacer(Modifier.height(18.dp)); Box(Modifier.size(80.dp).clip(CircleShape).background(Sky.copy(alpha = .12f)), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = Sky, strokeWidth = 3.dp, modifier = Modifier.size(42.dp)) }; Text("Understanding your project...", fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("Reading requirements\nIdentifying services\nBuilding project scope", color = Muted, textAlign = TextAlign.Center, lineHeight = 26.sp); Text("PREPARING A SMART START", color = Sky, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp) } }
            else -> AnalysisContent(onClose)
        }
    }
}

@Composable
private fun AnalysisContent(onClose: () -> Unit) { Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(horizontal = 22.dp).padding(bottom = 30.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) { Text("Project analysis", fontSize = 25.sp, fontWeight = FontWeight.Bold); Text("Here’s a clear starting point based on your brief.", color = Muted, fontSize = 13.sp); GlassCard { Column(Modifier.padding(17.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) { AnalysisRow("Project type", "Business application"); AnalysisRow("Required services", "App Development · Integration · Web Development"); Column { Text("COMPLEXITY", color = Faint, fontSize = 10.sp, letterSpacing = 1.sp); Spacer(Modifier.height(8.dp)); ProgressLine(68); Spacer(Modifier.height(5.dp)); Text("Medium / High", color = Sky, fontSize = 12.sp, fontWeight = FontWeight.Bold) }; AnalysisRow("Estimated timeline", "7–14 days"); AnalysisRow("Estimated budget", "₹25,000 – ₹45,000") } }; Text("Preliminary estimate — final quote confirmed manually.", color = Faint, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()); Button(onClick = onClose, modifier = Modifier.fillMaxWidth().height(54.dp), shape = RoundedCornerShape(16.dp)) { Text("REQUEST FINAL QUOTE", fontWeight = FontWeight.Bold); Spacer(Modifier.width(7.dp)); Icon(Icons.Outlined.ArrowForward, null) }; OutlinedButton(onClick = onClose, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = White)) { Text("SAVE & DISCUSS LATER") } } }

@Composable private fun AnalysisRow(label: String, value: String) { Column { Text(label.uppercase(), color = Faint, fontSize = 10.sp, letterSpacing = 1.sp); Spacer(Modifier.height(4.dp)); Text(value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold) } }
@Composable private fun Suggestion(text: String) { Surface(color = White.copy(alpha = .07f), shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, Border)) { Text(text, Modifier.padding(horizontal = 12.dp, vertical = 8.dp), color = Muted, fontSize = 11.sp) } }

@Composable fun GlassCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) { Surface(modifier = modifier.fillMaxWidth(), color = Glass, shape = RoundedCornerShape(23.dp), border = BorderStroke(1.dp, Border), shadowElevation = 8.dp) { Column(Modifier.fillMaxWidth(), content = content) } }
@Composable fun Avatar(initials: String, size: androidx.compose.ui.unit.Dp) { Box(Modifier.size(size).clip(CircleShape).background(androidx.compose.ui.graphics.Brush.linearGradient(listOf(Sky, DeepBlue3))).border(1.dp, White.copy(alpha = .28f), CircleShape), contentAlignment = Alignment.Center) { Text(initials, color = DeepBlue, fontSize = (size.value / 2.7f).sp, fontWeight = FontWeight.Bold) } }
@Composable fun ProgressLine(value: Int) { Box(Modifier.fillMaxWidth().height(7.dp).clip(CircleShape).background(White.copy(alpha = .1f))) { Box(Modifier.fillMaxWidth(value / 100f).fillMaxHeight().clip(CircleShape).background(Sky)) } }
@Composable fun SectionHeader(title: String, action: String) { Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, Modifier.weight(1f)); if (action.isNotBlank()) Text(action.uppercase(), color = Sky, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = .8.sp) } }
@Composable fun PageHeader(title: String, subtitle: String) { Column(verticalArrangement = Arrangement.spacedBy(6.dp)) { Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold); Text(subtitle, color = Muted, fontSize = 13.sp) } }
@Composable private fun SuggestionChip(text: String) { Text(text) }
@Composable private fun ProfileStat(value: String, label: String) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(value, color = Sky, fontWeight = FontWeight.Bold, fontSize = 19.sp); Text(label, color = Muted, fontSize = 10.sp) } }
@Composable private fun ProjectPreview() { GlassCard { Column(Modifier.padding(17.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(42.dp).clip(RoundedCornerShape(13.dp)).background(Sky.copy(alpha = .12f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Language, null, tint = Sky) }; Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("E-Commerce Website", fontWeight = FontWeight.Bold); Text("Web Development", color = Muted, fontSize = 11.sp) }; Text("82%", color = Sky, fontWeight = FontWeight.Bold) }; ProgressLine(82); Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Development in progress", color = Muted, fontSize = 11.sp); Text("Deadline Aug 22", color = Faint, fontSize = 11.sp) } } } }
@Composable private fun MetricCard(value: String, label: String, modifier: Modifier) { GlassCard(modifier) { Column(Modifier.padding(vertical = 14.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text(value, color = Sky, fontSize = 20.sp, fontWeight = FontWeight.Bold); Text(label, color = Muted, fontSize = 10.sp) } } }
EOF