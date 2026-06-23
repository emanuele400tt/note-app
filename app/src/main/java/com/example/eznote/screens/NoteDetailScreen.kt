package com.example.eznote.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eznote.components.EditNoteDialog
import com.example.eznote.model.NoteViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: String?,
    onBackClick: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val note = viewModel.selectedNote.collectAsState().value
    var showEditDialog by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = noteId) {
        noteId?.let { id ->
            viewModel.getNoteById(id)
        }
    }
    DisposableEffect(key1 = Unit) {
        onDispose {

            viewModel.clearSelectedNote()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dettaglio Nota") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Indietro")
                    }
                }
            )
        },
        floatingActionButton = {
            if (note != null) {
                FloatingActionButton(
                    onClick = { showEditDialog = true},
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Modifica Nota"
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (note == null) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                val formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.getDefault())
                    .withZone(ZoneId.systemDefault())
                Text(
                    text = formatter.format(note.entryDate),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = note.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        if(showEditDialog && note != null) {
            EditNoteDialog(
                note = note,
                onDismiss = {
                    showEditDialog = false
                },
                onConfirm = { newTitle, newDescription ->
                    val updatedNote = note.copy(
                        title = newTitle,
                        description = newDescription
                    )
                    viewModel.updateNote(updatedNote)
                    showEditDialog = false
                }
            )
        }
    }
}