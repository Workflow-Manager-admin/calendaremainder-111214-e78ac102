package com.example.calendaremainder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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


/*
 * --- MAIN ENTRY ---
 * Scaffold with TopBar and BottomBar; main content uses Material Cards for each feature block.
 */
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
        // Arrange each feature as a Material Card with spacing and theming
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background)
                .padding(padVals)
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            FeatureSectionMaterialCard(
                icon = Icons.Filled.DateRange,
                label = "Calendar View",
                color = AppColors.primary,
                buttonLabel = "View Calendar"
            )

            FeatureSectionMaterialCard(
                icon = Icons.Filled.EventAvailable,
                label = "Event Management",
                color = AppColors.secondary,
                buttonLabel = "Manage Events"
            )

            FeatureSectionMaterialCard(
                icon = Icons.Filled.Notifications,
                label = "Notifications",
                color = AppColors.accent,
                buttonLabel = "View Alerts"
            )

            FeatureSectionMaterialCard(
                icon = Icons.Filled.Person,
                label = "User Authentication",
                color = AppColors.primary,
                buttonLabel = "Login"
            )

            FeatureSectionMaterialCard(
                icon = Icons.Filled.CloudOff,
                label = "Offline Mode",
                color = AppColors.secondary,
                buttonLabel = "Sync/Offline"
            )
        }
    }
}

/**
 * PUBLIC_INTERFACE
 * FeatureSectionMaterialCard: A distinct Material Card for each main feature, using icon, title, button.
 * Button is placeholder (disabled), title uses section color, card surfaces are lightly elevated.
 */
@Composable
fun FeatureSectionMaterialCard(
    icon: ImageVector,
    label: String,
    color: Color,
    buttonLabel: String
) {
    Card(
        elevation = 8.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .background(color.copy(alpha = 0.14f), shape = RoundedCornerShape(8.dp))
                    .padding(14.dp)
            ) {
                Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(34.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = color
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = featureDescriptionForLabel(label),
                    color = color.copy(alpha = 0.88f),
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            // Interactive, but currently disabled placeholder button
            Button(
                onClick = { /* Placeholder. Actual actions TBD. */ },
                enabled = false,
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = color,
                    contentColor = Color.White,
                    disabledBackgroundColor = color.copy(alpha = 0.4f),
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                )
            ) {
                Text(buttonLabel, fontSize = 13.sp)
            }
        }
    }
}

/**
 * Returns a short placeholder description for each feature label.
 * All descriptions below are for visual guidance; can later be replaced with live content.
 */
fun featureDescriptionForLabel(label: String): String = when (label) {
    "Calendar View" -> "See your calendar by month, week, or day."
    "Event Management" -> "Add, edit, or delete events and reminders."
    "Notifications" -> "Never miss important events—see upcoming reminders."
    "User Authentication" -> "Login, register, and manage your profile."
    "Offline Mode" -> "Full access anywhere—even without internet."
    else -> ""
}

data class NavSection(val label: String, val icon: ImageVector)

/** App color constants following provided scheme. */
object AppColors {
    val primary = Color(0xFF4A90E2)
    val secondary = Color(0xFF50E3C2)
    val accent = Color(0xFFF5A623)
    val background = Color(0xFFF9F9F9)
}

/**
 * PUBLIC_INTERFACE
 * App-wide MaterialTheme with custom color scheme.
 */
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

/*
 * --- Placeholder Feature Screens (NOOP: UI Demos only for now, logic to be added in future) ---
 */

// PUBLIC_INTERFACE
@Composable
fun CalendarViewScreen() {/* NOOP placeholder, handled by card */}

// PUBLIC_INTERFACE
@Composable
fun EventManagementScreen() {/* NOOP placeholder, handled by card */}

// PUBLIC_INTERFACE
@Composable
fun NotificationsScreen() {/* NOOP placeholder, handled by card */}

// PUBLIC_INTERFACE
@Composable
fun AuthenticationScreen() {/* NOOP placeholder, handled by card */}

// PUBLIC_INTERFACE
@Composable
fun OfflineModeScreen() {/* NOOP placeholder, handled by card */}
