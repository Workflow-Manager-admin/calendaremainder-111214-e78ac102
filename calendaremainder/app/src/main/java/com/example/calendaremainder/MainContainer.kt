package com.example.calendaremainder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// PUBLIC_INTERFACE
class MainContainer : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendaRemainderAppTheme {
                MainContainerScaffold()
            }
        }
    }
}

@Composable
fun MainContainerScaffold() {
    val navItems = listOf(
        NavSection("Calendar", Icons.Filled.DateRange),
        NavSection("Events", Icons.Filled.EventAvailable),
        NavSection("Notifications", Icons.Filled.Notifications),
        NavSection("Account", Icons.Filled.Person)
    )
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = AppColors.primary,
                contentColor = Color.White,
                elevation = 8.dp
            ) {
                navItems.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(item.icon, item.label) },
                        label = { Text(item.label) },
                        selectedContentColor = AppColors.accent,
                        unselectedContentColor = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                backgroundColor = AppColors.primary,
                contentColor = Color.White,
                elevation = 8.dp,
                title = {
                    Text(
                        text = "CalendaRemainder",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            )
        }
    ) { padVals ->
        // New: Render all sections as visible placeholders stacked vertically for clear demonstration.
        // Refactored to show each feature as a visually distinct, icon-accented Material Card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background)
                .padding(padVals)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureSectionCard(
                icon = Icons.Filled.DateRange,
                label = "Calendar View",
                color = AppColors.primary,
                buttonLabel = "View Calendar"
            ) {
                CalendarViewScreen()
            }
            FeatureSectionCard(
                icon = Icons.Filled.EventAvailable,
                label = "Event Management",
                color = AppColors.secondary,
                buttonLabel = "Manage Events"
            ) {
                EventManagementScreen()
            }
            FeatureSectionCard(
                icon = Icons.Filled.Notifications,
                label = "Notifications",
                color = AppColors.accent,
                buttonLabel = "View Alerts"
            ) {
                NotificationsScreen()
            }
            FeatureSectionCard(
                icon = Icons.Filled.Person,
                label = "User Authentication",
                color = AppColors.primary,
                buttonLabel = "Login"
            ) {
                AuthenticationScreen()
            }
            FeatureSectionCard(
                icon = Icons.Filled.CloudOff,
                label = "Offline Mode",
                color = AppColors.secondary,
                buttonLabel = "Sync/Offline"
            ) {
                OfflineModeScreen()
            }
        }
    }
}

/**
 * Displays a visually distinct MaterialCard for a feature section with an icon, label, and placeholder button/action.
 */
// PUBLIC_INTERFACE
@Composable
fun FeatureSectionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    color: Color,
    buttonLabel: String,
    content: @Composable () -> Unit
) {
    Card(
        elevation = 6.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon (feature symbol)
            Box(
                Modifier
                    .background(color.copy(alpha = 0.15f), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = color
                )
                Spacer(modifier = Modifier.height(2.dp))
                // Place either real content or a minimal placeholder here
                Box(Modifier.fillMaxWidth()) {
                    content()
                }
            }
            Spacer(modifier = Modifier.width(6.dp))
            // Placeholder action Button (disabled for now)
            Button(
                onClick = { /* TODO: Activate action when feature is built */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = color, contentColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                enabled = false, // Placeholder only
                modifier = Modifier
                    .height(36.dp)
            ) {
                Text(buttonLabel, fontSize = 12.sp)
            }
        }
    }
}

// PUBLIC_INTERFACE
@Composable
fun OfflineModeScreen() {
    // Placeholder for offline/sync features
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            "Offline Mode (Local Storage/Sync)\n[TODO: Sync & offline logic UI]",
            color = AppColors.secondary
        )
    }
}

data class NavSection(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

/** App color constants following provided scheme. */
object AppColors {
    val primary = Color(0xFF4A90E2)
    val secondary = Color(0xFF50E3C2)
    val accent = Color(0xFFF5A623)
    val background = Color(0xFFF9F9F9)
}

@Composable
fun CalendaRemainderAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = AppColors.primary,
            primaryVariant = AppColors.primary,
            secondary = AppColors.secondary,
            secondaryVariant = AppColors.accent,
            background = AppColors.background,
            surface = Color.White,
            error = Color.Red,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.Black,
            onSurface = Color.Black,
            onError = Color.White
        ),
        typography = Typography(
            body1 = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
            h6 = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
        ),
        shapes = Shapes(
            medium = RoundedCornerShape(10.dp)
        ),
        content = content
    )
}

// --- Feature Screens (Minimal Placeholders) ---

// PUBLIC_INTERFACE
@Composable
fun CalendarViewScreen() {
    // Displays calendar in various views (month, week, day)
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Calendar View (month/week/day selector)\n[TODO: Calendar UI]", color = AppColors.primary)
    }
}

// PUBLIC_INTERFACE
@Composable
fun EventManagementScreen() {
    // Create, edit, delete events and reminders
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Event Management (Add/Edit/Delete)\n[TODO: Event CRUD UI]", color = AppColors.secondary)
    }
}

// PUBLIC_INTERFACE
@Composable
fun NotificationsScreen() {
    // Push notifications for upcoming events and reminders
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Notifications [TODO: Show reminders for events]", color = AppColors.accent)
    }
}

// PUBLIC_INTERFACE
@Composable
fun AuthenticationScreen() {
    // Login and user management features
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Authentication (Login/Logout/Profile)\n[TODO: Auth UI]", color = AppColors.primary)
    }
}

// --- Offline Mode Note ---
// For the minimal container, offline mode support will be realized in future subcomponents as local storage/sync logic.

