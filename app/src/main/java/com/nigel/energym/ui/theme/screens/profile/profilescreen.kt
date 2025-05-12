package com.nigel.energym.ui.theme.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nigel.energym.data.ProfileUiState
import com.nigel.energym.data.ProfileViewModel
import com.nigel.energym.navigation.ROUTE_HOME

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profilescreen(navController: NavHostController) {
    // Initialize Firebase instances
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    // Create ViewModel instance directly with Firebase dependencies
    val viewModel = remember { ProfileViewModel(auth, database) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isEditing by remember { mutableStateOf(false) }

    // Temporary edit state
    var editedName by remember { mutableStateOf("") }
    var editedHeight by remember { mutableStateOf("") }
    var editedWeight by remember { mutableStateOf("") }
    var editedAge by remember { mutableStateOf("") }
    var editedGoal by remember { mutableStateOf("") }
    var editedActivityLevel by remember { mutableStateOf("") }

    // Photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateProfilePhoto(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUTE_HOME) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                        }
                    } else {
                        TextButton(onClick = {
                            // Save changes
                            viewModel.updateProfile(
                                name = editedName.takeIf { it.isNotBlank() },
                                height = editedHeight.toIntOrNull(),
                                weight = editedWeight.toFloatOrNull(),
                                age = editedAge.toIntOrNull(),
                                goal = editedGoal.takeIf { it.isNotBlank() },
                                activityLevel = editedActivityLevel.takeIf { it.isNotBlank() }
                            )
                            isEditing = false
                        }) {
                            Text("Save")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is ProfileUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ProfileUiState.Success -> {
                val userData = state.userData

                // Initialize edit fields when entering edit mode
                LaunchedEffect(isEditing) {
                    if (isEditing) {
                        editedName = userData.name
                        editedHeight = userData.height.toString()
                        editedWeight = userData.weight.toString()
                        editedAge = userData.age.toString()
                        editedGoal = userData.goal
                        editedActivityLevel = userData.activityLevel
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Photo
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable {
                                if (isEditing) {
                                    photoPickerLauncher.launch("image/*")
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(userData.photoUrl.ifBlank { null })
                                    .fallback(android.R.drawable.ic_menu_camera)
                                    .build()
                            ),
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        if (isEditing) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Photo",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Profile Details
                    ProfileDetailItem(
                        label = "Name",
                        value = if (isEditing) editedName else userData.name,
                        onValueChange = { editedName = it },
                        isEditing = isEditing
                    )

                    ProfileDetailItem(
                        label = "Height (cm)",
                        value = if (isEditing) editedHeight else userData.height.toString(),
                        onValueChange = { editedHeight = it },
                        isEditing = isEditing,
                        keyboardType = KeyboardType.Number
                    )

                    ProfileDetailItem(
                        label = "Weight (kg)",
                        value = if (isEditing) editedWeight else userData.weight.toString(),
                        onValueChange = { editedWeight = it },
                        isEditing = isEditing,
                        keyboardType = KeyboardType.Decimal
                    )

                    ProfileDetailItem(
                        label = "Age",
                        value = if (isEditing) editedAge else userData.age.toString(),
                        onValueChange = { editedAge = it },
                        isEditing = isEditing,
                        keyboardType = KeyboardType.Number
                    )

                    ProfileDetailItem(
                        label = "Fitness Goal",
                        value = if (isEditing) editedGoal else userData.goal,
                        onValueChange = { editedGoal = it },
                        isEditing = isEditing
                    )

                    ProfileDetailItem(
                        label = "Activity Level",
                        value = if (isEditing) editedActivityLevel else userData.activityLevel,
                        onValueChange = { editedActivityLevel = it },
                        isEditing = isEditing
                    )

                    // Workout Stats
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Workout Statistics",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    WorkoutStatCard(
                        workoutsCompleted = userData.workoutsCompleted,
                        caloriesBurned = userData.caloriesBurned,
                        minutesExercised = userData.minutesExercised,
                        streakDays = userData.streakDays
                    )
                }
            }
            is ProfileUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error Loading Profile",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = { viewModel.clearErrorState() },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileDetailItem(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditing: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        if (isEditing) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                singleLine = true
            )
        } else {
            Text(
                text = value.ifBlank { "Not set" },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(12.dp)
            )
        }
    }
}

@Composable
fun WorkoutStatCard(
    workoutsCompleted: Int,
    caloriesBurned: Int,
    minutesExercised: Int,
    streakDays: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WorkoutStatItem(
                value = workoutsCompleted.toString(),
                label = "Workouts",
                modifier = Modifier.weight(1f)
            )
            WorkoutStatItem(
                value = caloriesBurned.toString(),
                label = "Calories",
                modifier = Modifier.weight(1f)
            )
            WorkoutStatItem(
                value = minutesExercised.toString(),
                label = "Minutes",
                modifier = Modifier.weight(1f)
            )
            WorkoutStatItem(
                value = streakDays.toString(),
                label = "Streak",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun WorkoutStatItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.nigel.energym.data.ProfileUiState
//import com.nigel.energym.data.ProfileViewModel
//
//import coil.compose.rememberAsyncImagePainter
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
////import androidx.hilt.navigation.compose.hiltViewModel
//import coil.request.ImageRequest
//import com.nigel.energym.navigation.ROUTE_HOME
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Profilescreen(  viewModel: ProfileViewModel = viewModel(),navController: NavHostController) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    var isEditing by remember { mutableStateOf(false) }
//
//    // Temporary edit state
//    var editedName by remember { mutableStateOf("") }
//    var editedHeight by remember { mutableStateOf("") }
//    var editedWeight by remember { mutableStateOf("") }
//    var editedAge by remember { mutableStateOf("") }
//    var editedGoal by remember { mutableStateOf("") }
//    var editedActivityLevel by remember { mutableStateOf("") }
//
//    // Photo picker launcher
//    val photoPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            viewModel.updateProfilePhoto(it)
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Profile") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigate(ROUTE_HOME) }) {
//                        Icon(Icons.Default.Person, contentDescription = "Back")
//                    }
//                },
//                actions = {
//                    if (!isEditing) {
//                        IconButton(onClick = { isEditing = true }) {
//                            Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
//                        }
//                    } else {
//                        TextButton(onClick = {
//                            // Save changes
//                            viewModel.updateProfile(
//                                name = editedName.takeIf { it.isNotBlank() },
//                                height = editedHeight.toIntOrNull(),
//                                weight = editedWeight.toFloatOrNull(),
//                                age = editedAge.toIntOrNull(),
//                                goal = editedGoal.takeIf { it.isNotBlank() },
//                                activityLevel = editedActivityLevel.takeIf { it.isNotBlank() }
//                            )
//                            isEditing = false
//                        }) {
//                            Text("Save")
//                        }
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        when (val state = uiState) {
//            is ProfileUiState.Loading -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//            is ProfileUiState.Success -> {
//                val userData = state.userData
//
//                // Initialize edit fields when entering edit mode
//                LaunchedEffect(isEditing) {
//                    if (isEditing) {
//                        editedName = userData.name
//                        editedHeight = userData.height.toString()
//                        editedWeight = userData.weight.toString()
//                        editedAge = userData.age.toString()
//                        editedGoal = userData.goal
//                        editedActivityLevel = userData.activityLevel
//                    }
//                }
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues)
//                        .verticalScroll(rememberScrollState())
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    // Profile Photo
//                    Box(
//                        modifier = Modifier
//                            .size(120.dp)
//                            .clip(CircleShape)
//                            .background(MaterialTheme.colorScheme.surfaceVariant)
//                            .clickable {
//                                if (isEditing) {
//                                    photoPickerLauncher.launch("image/*")
//                                }
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Image(
//                            painter = rememberAsyncImagePainter(
//                                ImageRequest.Builder(LocalContext.current)
//                                    .data(userData.photoUrl.ifBlank { null })
//                                    .fallback(android.R.drawable.ic_menu_camera)
//                                    .build()
//                            ),
//                            contentDescription = "Profile Photo",
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .clip(CircleShape),
//                            contentScale = ContentScale.Crop
//                        )
//                        if (isEditing) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .background(Color.Black.copy(alpha = 0.5f)),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Edit,
//                                    contentDescription = "Edit Photo",
//                                    tint = Color.White
//                                )
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Profile Details
//                    ProfileDetailItem(
//                        label = "Name",
//                        value = if (isEditing) editedName else userData.name,
//                        onValueChange = { editedName = it },
//                        isEditing = isEditing
//                    )
//
//                    ProfileDetailItem(
//                        label = "Height (cm)",
//                        value = if (isEditing) editedHeight else userData.height.toString(),
//                        onValueChange = { editedHeight = it },
//                        isEditing = isEditing,
//                        keyboardType = KeyboardType.Number
//                    )
//
//                    ProfileDetailItem(
//                        label = "Weight (kg)",
//                        value = if (isEditing) editedWeight else userData.weight.toString(),
//                        onValueChange = { editedWeight = it },
//                        isEditing = isEditing,
//                        keyboardType = KeyboardType.Decimal
//                    )
//
//                    ProfileDetailItem(
//                        label = "Age",
//                        value = if (isEditing) editedAge else userData.age.toString(),
//                        onValueChange = { editedAge = it },
//                        isEditing = isEditing,
//                        keyboardType = KeyboardType.Number
//                    )
//
//                    ProfileDetailItem(
//                        label = "Fitness Goal",
//                        value = if (isEditing) editedGoal else userData.goal,
//                        onValueChange = { editedGoal = it },
//                        isEditing = isEditing
//                    )
//
//                    ProfileDetailItem(
//                        label = "Activity Level",
//                        value = if (isEditing) editedActivityLevel else userData.activityLevel,
//                        onValueChange = { editedActivityLevel = it },
//                        isEditing = isEditing
//                    )
//
//                    // Workout Stats
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Text(
//                        text = "Workout Statistics",
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//
//                    WorkoutStatCard(
//                        workoutsCompleted = userData.workoutsCompleted,
//                        caloriesBurned = userData.caloriesBurned,
//                        minutesExercised = userData.minutesExercised,
//                        streakDays = userData.streakDays
//                    )
//                }
//            }
//            is ProfileUiState.Error -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Error Loading Profile",
//                            style = MaterialTheme.typography.titleMedium,
//                            color = MaterialTheme.colorScheme.error
//                        )
//                        Text(
//                            text = state.message,
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = MaterialTheme.colorScheme.error
//                        )
//                        Button(
//                            onClick = { viewModel.clearErrorState() },
//                            modifier = Modifier.padding(top = 16.dp)
//                        ) {
//                            Text("Retry")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProfileDetailItem(
//    label: String,
//    value: String,
//    onValueChange: (String) -> Unit,
//    isEditing: Boolean,
//    keyboardType: KeyboardType = KeyboardType.Text
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//    ) {
//        Text(
//            text = label,
//            style = MaterialTheme.typography.bodyMedium,
//            modifier = Modifier.padding(bottom = 4.dp)
//        )
//        if (isEditing) {
//            TextField(
//                value = value,
//                onValueChange = onValueChange,
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
//                singleLine = true
//            )
//        } else {
//            Text(
//                text = value.ifBlank { "Not set" },
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        MaterialTheme.colorScheme.surfaceVariant,
//                        shape = RoundedCornerShape(4.dp)
//                    )
//                    .padding(12.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun WorkoutStatCard(
//    workoutsCompleted: Int,
//    caloriesBurned: Int,
//    minutesExercised: Int,
//    streakDays: Int
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            WorkoutStatItem(
//                value = workoutsCompleted.toString(),
//                label = "Workouts",
//                modifier = Modifier.weight(1f)
//            )
//            WorkoutStatItem(
//                value = caloriesBurned.toString(),
//                label = "Calories",
//                modifier = Modifier.weight(1f)
//            )
//            WorkoutStatItem(
//                value = minutesExercised.toString(),
//                label = "Minutes",
//                modifier = Modifier.weight(1f)
//            )
//            WorkoutStatItem(
//                value = streakDays.toString(),
//                label = "Streak",
//                modifier = Modifier.weight(1f)
//            )
//        }
//    }
//}
//
//@Composable
//fun WorkoutStatItem(
//    value: String,
//    label: String,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//    ) {
//        Text(
//            text = value,
//            style = MaterialTheme.typography.titleMedium
//        )
//        Text(
//            text = label,
//            style = MaterialTheme.typography.bodySmall
//        )
//    }
//}


//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Profile") },
//                actions = {
//                    IconButton(onClick = { /* Open settings */ }) {
//                        Icon(Icons.Default.Settings, contentDescription = "Settings")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        when (val state = uiState) {
//            is ProfileUiState.Loading -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//            is ProfileUiState.Error -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = state.message,
//                        color = MaterialTheme.colorScheme.error
//                    )
//                }
//            }
//            is ProfileUiState.Success -> {
//                val userData = state.userData
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues)
//                        .verticalScroll(rememberScrollState()),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    // Profile header
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(MaterialTheme.colorScheme.primaryContainer)
//                            .padding(vertical = 24.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Box {
//                                Image(
//                                    painter = rememberAsyncImagePainter(model = userData.photoUrl),
//                                    contentDescription = "Profile picture",
//                                    modifier = Modifier
//                                        .size(120.dp)
//                                        .clip(CircleShape),
//                                    contentScale = ContentScale.Crop
//                                )
//
//                                IconButton(
//                                    onClick = { /* Edit profile picture */ },
//                                    modifier = Modifier
//                                        .align(Alignment.BottomEnd)
//                                        .size(40.dp)
//                                        .clip(CircleShape)
//                                        .background(MaterialTheme.colorScheme.primary)
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Outlined.Edit,
//                                        contentDescription = "Edit picture",
//                                        tint = MaterialTheme.colorScheme.onPrimary
//                                    )
//                                }
//                            }
//
//                            Spacer(modifier = Modifier.height(16.dp))
//
//                            Text(
//                                text = userData.name,
//                                style = MaterialTheme.typography.headlineMedium,
//                                fontWeight = FontWeight.Bold
//                            )
//
//                            Spacer(modifier = Modifier.height(8.dp))
//
//                            Text(
//                                text = "Goal: ${userData.goal}",
//                                style = MaterialTheme.typography.bodyLarge
//                            )
//
//                            Spacer(modifier = Modifier.height(4.dp))
//
//                            Text(
//                                text = "Level: ${userData.activityLevel}",
//                                style = MaterialTheme.typography.bodyLarge
//                            )
//                        }
//                    }
//
//                    // Stats cards
//                    StatsSection(userData)
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Physical info section
//                    PhysicalInfoSection(userData, viewModel)
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Preferences section
//                    PreferencesSection(userData, viewModel)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun StatsSection(userData: UserProfileData) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "Your Stats",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                StatItem(
//                    icon = Icons.Default.Refresh,
//                    value = userData.workoutsCompleted.toString(),
//                    label = "Workouts",
//                    modifier = Modifier.weight(1f)
//                )
//
//                StatItem(
//                    icon = Icons.Default.Warning,
//                    value = "${userData.caloriesBurned}",
//                    label = "Calories",
//                    modifier = Modifier.weight(1f)
//                )
//
//                StatItem(
//                    icon = Icons.Default.Refresh,
//                    value = "${userData.minutesExercised}",
//                    label = "Minutes",
//                    modifier = Modifier.weight(1f)
//                )
//
//                StatItem(
//                    icon = Icons.Default.DateRange,
//                    value = "${userData.streakDays}",
//                    label = "Streak",
//                    modifier = Modifier.weight(1f)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun StatItem(
//    icon: androidx.compose.ui.graphics.vector.ImageVector,
//    value: String,
//    label: String,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier.padding(horizontal = 4.dp)
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.primary
//        )
//
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(
//            text = value,
//            style = MaterialTheme.typography.titleMedium,
//            fontWeight = FontWeight.Bold
//        )
//
//        Text(
//            text = label,
//            style = MaterialTheme.typography.bodySmall,
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//@Composable
//fun PhysicalInfoSection(
//    userData: UserProfileData,
//    viewModel: ProfileViewModel
//) {
//    var showWeightDialog by remember { mutableStateOf(false) }
//    var showHeightDialog by remember { mutableStateOf(false) }
//    var showAgeDialog by remember { mutableStateOf(false) }
//
//    var weightInput by remember { mutableStateOf(userData.weight.toString()) }
//    var heightInput by remember { mutableStateOf(userData.height.toString()) }
//    var ageInput by remember { mutableStateOf(userData.age.toString()) }
//
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "Physical Info",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            InfoItem(
//                label = "Height",
//                value = "${userData.height} cm",
//                onEditClick = { showHeightDialog = true }
//            )
//
//            Divider(modifier = Modifier.padding(vertical = 8.dp))
//
//            InfoItem(
//                label = "Weight",
//                value = "${userData.weight} kg",
//                onEditClick = { showWeightDialog = true }
//            )
//
//            Divider(modifier = Modifier.padding(vertical = 8.dp))
//
//            InfoItem(
//                label = "Age",
//                value = "${userData.age} years",
//                onEditClick = {showAgeDialog = true}
//            )
//        }
//    }
//
//    if (showWeightDialog) {
//        AlertDialog(
//            onDismissRequest = { showWeightDialog = false },
//            title = { Text("Update Weight") },
//            text = {
//                TextField(
//                    value = weightInput,
//                    onValueChange = { weightInput = it },
//                    label = { Text("Weight (kg)") },
//                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
//                )
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        weightInput.toFloatOrNull()?.let { viewModel.updateWeight(it) }
//                        showWeightDialog = false
//                    }
//                ) {
//                    Text("Save")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showWeightDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//
//    if (showHeightDialog) {
//        AlertDialog(
//            onDismissRequest = { showHeightDialog = false },
//            title = { Text("Update Weight") },
//            text = {
//                TextField(
//                    value = heightInput,
//                    onValueChange = { heightInput = it },
//                    label = { Text("Height (cm)") },
//                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
//                )
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        heightInput.toIntOrNull()?.let { viewModel.updateHeight(it) }
//                        showHeightDialog = false
//                    }
//                ) {
//                    Text("Save")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showHeightDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//
//    if (showAgeDialog) {
//        AlertDialog(
//            onDismissRequest = { showAgeDialog = false },
//            title = { Text("Update Age") },
//            text = {
//                TextField(
//                    value = ageInput,
//                    onValueChange = { ageInput = it },
//                    label = { Text("Age (yrs)") },
//                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
//                )
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        ageInput.toIntOrNull()?.let { viewModel.updateAge(it) }
//                        showAgeDialog = false
//                    }
//                ) {
//                    Text("Save")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showAgeDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//}
//
//@Composable
//fun InfoItem(
//    label: String,
//    value: String,
//    onEditClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = label,
//            style = MaterialTheme.typography.bodyLarge
//        )
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(
//                text = value,
//                style = MaterialTheme.typography.bodyLarge,
//                fontWeight = FontWeight.Medium
//            )
//
//            IconButton(onClick = onEditClick) {
//                Icon(
//                    imageVector = Icons.Default.Edit,
//                    contentDescription = "Edit $label",
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun PreferencesSection(
//    userData: UserProfileData,
//    viewModel: ProfileViewModel
//) {
//    var showGoalDialog by remember { mutableStateOf(false) }
//    var showLevelDialog by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "Fitness Preferences",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            InfoItem(
//                label = "Fitness Goal",
//                value = userData.goal,
//                onEditClick = { showGoalDialog = true }
//            )
//
//            Divider(modifier = Modifier.padding(vertical = 8.dp))
//
//            InfoItem(
//                label = "Activity Level",
//                value = userData.activityLevel,
//                onEditClick = { showLevelDialog = true }
//            )
//        }
//    }
//
//    // Goal selection dialog
//    if (showGoalDialog) {
//        val goals = listOf("Lose Weight", "Maintain", "Build Muscle", "Improve Endurance")
//
//        AlertDialog(
//            onDismissRequest = { showGoalDialog = false },
//            title = { Text("Select Fitness Goal") },
//            text = {
//                Column {
//                    goals.forEach { goal ->
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 8.dp)
//                                .clickable {
//                                    viewModel.updateGoal(goal)
//                                    showGoalDialog = false
//                                },
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            RadioButton(
//                                selected = goal == userData.goal,
//                                onClick = {
//                                    viewModel.updateGoal(goal)
//                                    showGoalDialog = false
//                                }
//                            )
//                            Text(text = goal, modifier = Modifier.padding(start = 8.dp))
//                        }
//                    }
//                }
//            },
//            confirmButton = {},
//            dismissButton = {
//                TextButton(onClick = { showGoalDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//
//    // Activity level dialog
//    if (showLevelDialog) {
//        val levels = listOf("Beginner", "Intermediate", "Advanced", "Expert")
//
//        AlertDialog(
//            onDismissRequest = { showLevelDialog = false },
//            title = { Text("Select Activity Level") },
//            text = {
//                Column {
//                    levels.forEach { level ->
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 8.dp)
//                                .clickable {
//                                    viewModel.updateActivityLevel(level)
//                                    showLevelDialog = false
//                                },
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            RadioButton(
//                                selected = level == userData.activityLevel,
//                                onClick = {
//                                    viewModel.updateActivityLevel(level)
//                                    showLevelDialog = false
//                                }
//                            )
//                            Text(text = level, modifier = Modifier.padding(start = 8.dp))
//                        }
//                    }
//                }
//            },
//            confirmButton = {},
//            dismissButton = {
//                TextButton(onClick = { showLevelDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//}



























//    val context = LocalContext.current
//    val auth = FirebaseAuth.getInstance()
//    val user = auth.currentUser
//    val email = user?.email ?: "No Email"
//    val light_blue = colorResource(id = R.color.light_blue)
//
//    var weightInput by remember { mutableStateOf("") }
//    var heightInput by remember { mutableStateOf("") }
//    var bmiResult by remember { mutableStateOf("") }
//    var exerciseSuggestion by remember { mutableStateOf("") }
//
//    val database = FirebaseDatabase.getInstance().reference
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = light_blue) // Light blue background
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Text(
//            text = "Profile",
//            style = MaterialTheme.typography.headlineMedium,
//            color = Color(0xFF01579B) // Dark blue
//        )
//
//        Text(
//            text = "Email: $email",
//            style = MaterialTheme.typography.bodyLarge,
//            color = Color.Gray
//        )
//
//        OutlinedTextField(
//            value = weightInput,
//            onValueChange = { weightInput = it },
//            label = { Text("Weight (kg)") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            modifier = Modifier.fillMaxWidth(),
//            shape =   RoundedCornerShape(40.dp)
//        )
//
//        OutlinedTextField(
//            value = heightInput,
//            onValueChange = { heightInput = it },
//            label = { Text("Height (cm)") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            modifier = Modifier.fillMaxWidth(),
//            shape =   RoundedCornerShape(40.dp)
//
//        )
//
//        Button(
//            onClick = {
//                val weight = weightInput.toFloatOrNull()
//                val heightCm = heightInput.toFloatOrNull()
//                if (weight != null && heightCm != null && heightCm > 0) {
//                    val heightM = heightCm / 100
//                    val bmi = weight / (heightM * heightM)
//                    val bmiCategory = when {
//                        bmi < 18.5 -> "Underweight"
//                        bmi < 25 -> "Normal weight"
//                        bmi < 30 -> "Overweight"
//                        else -> "Obese"
//                    }
//                    bmiResult = "BMI: %.2f (%s)".format(bmi, bmiCategory)
//                } else {
//                    bmiResult = "Please enter valid weight and height."
//                }
//            },
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)), // Blue
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Calculate BMI")
//        }
//
//        Text(
//            text = bmiResult,
//            style = MaterialTheme.typography.bodyLarge,
//            color = Color.DarkGray
//        )
//
//        OutlinedTextField(
//            value = exerciseSuggestion,
//            onValueChange = { exerciseSuggestion = it },
//            label = { Text("Suggest a New Exercise") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Button(
//            onClick = {
//                val suggestion = exerciseSuggestion.trim()
//                if (suggestion.isNotEmpty()) {
//                    val suggestionRef = database.child("exerciseSuggestions").push()
//                    val suggestionData = mapOf(
//                        "email" to email,
//                        "suggestion" to suggestion
//                    )
//                    suggestionRef.setValue(suggestionData)
//                    exerciseSuggestion = ""
//                    // Optionally, show a confirmation message
//                }
//            },
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)), // Blue
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Submit Suggestion")
//        }
//    }
//
//
////    val viewModel: ProfileViewModel = viewModel()
////    val userData = viewModel.userData.collectAsState().value
////    val quote = viewModel.quote.collectAsState().value
////    val context = LocalContext.current
////
////    userData?.let { user ->
////        Profilescreen(
////            userName = user.name,
////            profilePicUrl = user.profilePicUrl,
////            height = user.height,
////            weight = user.weight,
////            quote = quote,
////            onFetchQuote = { viewModel.fetchRandomQuote() }
////        )
////    } ?: run {
////        // Show loading or fallback UI
////        Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
////            CircularProgressIndicator()
////        }
////    }
////
////}
////
////@Composable
////fun Profilescreen(
////    userName: String,
////    profilePicUrl: String?,
////    height: Int,
////    weight: Int,
////    quote: String?,
////    onFetchQuote: () -> Unit
////) {
////
////    Column(
////        modifier = Modifier.fillMaxSize(),
////        horizontalAlignment = Alignment.CenterHorizontally
////    ) {
//////        AsyncImage(model = profilePicUrl, contentDescription = "Profile Picture")
////        Text(text = userName, style = MaterialTheme.typography.headlineLarge)
////        Text(text = "Height: $height cm")
////        Text(text = "Weight: $weight kg")
////        Text(text = "Quote: ${quote ?: "No quote available"}")
////        Button(onClick = onFetchQuote) {
////            Text("Fetch New Quote")
////        }
////    }
////
////}
//}

//@Preview
//@Composable
//private fun Profilepage(){
//    Profilescreen(  )
//}