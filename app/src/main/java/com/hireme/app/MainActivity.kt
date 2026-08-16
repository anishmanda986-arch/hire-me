package com.hireme.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainShell(tab: Tab, onTab: (Tab) -> Unit, onRequest: () -> Unit) {
    Scaffold(containerColor = Color.Transparent, bottomBar = { BottomNav(tab, onTab) }) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            AnimatedContent(tab, transitionSpec = { fadeIn() togetherWith fadeOut() }, label = "tab") { selected ->
                when (selected) { Tab.HOME -> HomeScreen(onRequest); Tab.PROJECTS -> ProjectsScreen(); Tab.SERVICES -> ServicesScreen(onRequest); Tab.MESSAGES -> MessagesScreen(); Tab.PROFILE -> ProfileScreen() }
            }
        }
    }
}

@Composable
private fun BottomNav(tab: Tab, onTab: (Tab) -> Unit) {
    Surface(Modifier.padding(horizontal = 14.dp, vertical = 10.dp).navigationBarsPadding(), shape = RoundedCornerShape(24.dp), color = DeepBlue2.copy(alpha = .94f), border = BorderStroke(1.dp, Border), shadowElevation = 14.dp) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 7.dp, vertical = 7.dp), horizontalArrangement = Arrangement.SpaceAround) { Tab.values().forEach { item -> Column(Modifier.weight(1f).clickable { onTab(item) }.padding(vertical = 7.dp), horizontalAlignment = Alignment.CenterHorizontally) { Icon(item.icon, item.label, tint = if (tab == item) Sky else Faint, modifier = Modifier.size(21.dp)); Text(item.label, fontSize = 10.sp, color = if (tab == item) Sky else Faint, fontWeight = if (tab == item) FontWeight.Bold else FontWeight.Normal) } } }
    }
}

@Composable
private fun HomeScreen(onRequest: () -> Unit) {
    androidx.compose.foundation.lazy.LazyColumn(Modifier.fillMaxSize().padding(horizontal = 20.dp), contentPadding = PaddingValues(top = 24.dp, bottom = 28.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        item { Row(verticalAlignment = Alignment.CenterVertically) { Avatar("A", 48.dp); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Hi, Anish 👋", fontSize = 21.sp, fontWeight = FontWeight.Bold); Text("Available for new projects", color = Sky, fontSize = 12.sp) }; IconButton(onClick = {}) { Icon(Icons.Outlined.NotificationsNone, "Notifications", tint = White) } } }
        item { HeroCard(onRequest) }
        item { SectionHeader("What can I help you with?", "View all") }
        item { ServiceGrid(onRequest) }
        item { SectionHeader("Active project", "See all") }
        item { ProjectPreview() }
        item { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) { MetricCard("32", "Projects", Modifier.weight(1f)); MetricCard("4.9", "Rating", Modifier.weight(1f)); MetricCard("98%", "Completion", Modifier.weight(1f)) } }
    }
}

@Composable
private fun HeroCard(onRequest: () -> Unit) {
    Column(Modifier.fillMaxWidth().background(DeepBlue3, RoundedCornerShape(28.dp)).padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) { Column(Modifier.weight(1f)) { Text("Have a project in mind?", fontSize = 23.sp, fontWeight = FontWeight.Bold); Text("Tell me what you need.", color = Muted, fontSize = 14.sp) }; Icon(Icons.Outlined.NorthEast, null, tint = Sky) }
        Row(Modifier.fillMaxWidth().background(White.copy(alpha = .08f), RoundedCornerShape(17.dp)).clickable { onRequest() }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Text("Describe your project or problem...", Modifier.weight(1f), color = Faint, fontSize = 13.sp); Icon(Icons.Outlined.Mic, "Use voice", tint = Sky) }
        Button(onClick = onRequest, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(15.dp)) { Text("GET A QUOTE", fontWeight = FontWeight.Bold, letterSpacing = .8.sp); Spacer(Modifier.width(8.dp)); Icon(Icons.Outlined.ArrowForward, null) }
    }
}

@Composable
private fun ServiceGrid(onRequest: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(11.dp)) { services.chunked(2).forEach { row -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(11.dp)) { row.forEach { service -> ServiceTile(service, Modifier.weight(1f), onRequest) } } } }
}

@Composable
private fun ServiceTile(service: Service, modifier: Modifier = Modifier, onRequest: () -> Unit) {
    GlassCard(modifier.clickable { onRequest() }.height(166.dp)) { Column(Modifier.fillMaxSize().padding(15.dp)) { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Icon(service.icon, service.title, tint = Sky, modifier = Modifier.size(22.dp)); Icon(Icons.Outlined.NorthEast, null, tint = Muted, modifier = Modifier.size(16.dp)) }; Spacer(Modifier.weight(1f)); Text(service.title, fontWeight = FontWeight.Bold, fontSize = 15.sp); Spacer(Modifier.height(5.dp)); Text(service.description, color = Muted, fontSize = 11.sp, lineHeight = 15.sp, maxLines = 2) } }
}

@Composable
private fun ProjectsScreen() {
    androidx.compose.foundation.lazy.LazyColumn(Modifier.fillMaxSize().padding(horizontal = 20.dp), contentPadding = PaddingValues(top = 26.dp, bottom = 30.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) { item { PageHeader("My Projects", "Everything you're building with me.") }; item { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) { FilterPill("All", true); FilterPill("In progress", false); FilterPill("Completed", false) } }; items(projects) { ProjectCard(it) } }
}

@Composable
private fun ProjectCard(project: Project) {
    GlassCard { Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) { Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(43.dp).background(Sky.copy(alpha = .13f), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) { Icon(if (project.done) Icons.Outlined.CheckCircle else Icons.Outlined.FolderOpen, null, tint = Sky) }; Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text(project.title, fontWeight = FontWeight.Bold, fontSize = 16.sp); Text(project.service, color = Muted, fontSize = 12.sp) }; Icon(Icons.Outlined.MoreHoriz, "More", tint = Muted) }; Row(verticalAlignment = Alignment.Bottom) { Column(Modifier.weight(1f)) { Text(if (project.done) "COMPLETED" else "IN PROGRESS", color = Sky, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp); Spacer(Modifier.height(7.dp)); ProgressLine(project.progress) }; Spacer(Modifier.width(16.dp)); Text("${project.progress}%", color = Sky, fontWeight = FontWeight.Bold, fontSize = 20.sp) }; Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(if (project.done) "Delivered successfully" else "Deadline  ${project.deadline}", color = Muted, fontSize = 12.sp); Text("OPEN PROJECT  ›", color = Sky, fontSize = 11.sp, fontWeight = FontWeight.Bold) } } }
}

@Composable
private fun ServicesScreen(onRequest: () -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp), contentPadding = PaddingValues(top = 26.dp, bottom = 30.dp), horizontalArrangement = Arrangement.spacedBy(11.dp), verticalArrangement = Arrangement.spacedBy(11.dp)) { item(span = { GridItemSpan(2) }) { PageHeader("Services", "Specialist support, from idea to delivery.") }; items(services) { service -> ServiceTile(service, Modifier.fillMaxWidth(), onRequest) } }
}

@Composable
private fun FilterPill(text: String, active: Boolean) { Surface(color = if (active) Sky.copy(alpha = .16f) else Glass, shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, if (active) Sky.copy(alpha = .35f) else Border)) { Text(text, Modifier.padding(horizontal = 14.dp, vertical = 9.dp), color = if (active) Sky else Muted, fontSize = 11.sp, fontWeight = if (active) FontWeight.Bold else FontWeight.Normal) } }
