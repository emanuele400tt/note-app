package com.example.eznote.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eznote.R
import com.example.eznote.components.AddNodeButton
import com.example.eznote.components.NoteCard
import com.example.eznote.components.NoteInputText
import com.example.eznote.components.NoteScreenAppBar
import com.example.eznote.data.NotesDataSource
import com.example.eznote.model.Note
import com.example.eznote.model.NoteViewModel
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNoteClick: (String) -> Unit
) {
    val notes = viewModel.noteList.collectAsState().value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val addMessage = stringResource(R.string.note_added) // per launch
    val delMessage = stringResource(R.string.note_deleted)

    var noteToDelete by remember { mutableStateOf<Note?>(null) }
    if( noteToDelete != null) {
        AlertDialog(
            onDismissRequest = { noteToDelete = null},
            title = { Text("Elimina nota") },
            text = { Text("Sei sicuro di voler eliminare questa nota, l'operazione è irreversibile")},
            confirmButton = {
                TextButton(onClick = {
                    noteToDelete?.let { viewModel.removeNote(it) }
                    noteToDelete = null
                    scope.launch { snackbarHostState.showSnackbar(delMessage) }
                }) {
                    Text("Elimina", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { noteToDelete = null }) {
                    Text("Annulla", color = MaterialTheme.colorScheme.onBackground)
                }
            }

        )
    }

    Scaffold(
        topBar = { NoteScreenAppBar() }
    ) { paddingValue ->
        NoteContent(
            modifier = modifier.padding(paddingValue),
            notes = notes,
            title = viewModel.title,
            description = viewModel.description,
            onTitleChange = { viewModel.onTitleChange(it) },
            onDescriptionChange = { viewModel.onDescriptionChange(it) },
            onAddNote = {
                viewModel.addNote(it)
                Toast.makeText(context, addMessage, Toast.LENGTH_SHORT).show()

                viewModel.onTitleChange("")
                viewModel.onDescriptionChange("")
            },
            onRemoveNote = {note ->
                noteToDelete = note
            },
            onNoteClick = onNoteClick
        )
    }
}

@Composable
fun NoteContent(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
    onNoteClick: (String) -> Unit,
) {

    val focusMenager = LocalFocusManager.current
    Column(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier = Modifier.padding(9.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) onTitleChange(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardAction = KeyboardActions(onNext = {
                    focusMenager.moveFocus(FocusDirection.Down)
                }),

            )
            NoteInputText(
                modifier = Modifier.padding((9.dp)),
                text = description,
                label = "Add a note",
                maxLine = 3,
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) onDescriptionChange(it)
                },
            )
            Spacer(modifier = Modifier.height(4.dp))
            AddNodeButton(
                text = "Save",
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        onAddNote(Note(title = title, description = description))
                        onTitleChange("")
                        onDescriptionChange("")
                        focusMenager.clearFocus()
                    }
                })
        }
        HorizontalDivider(modifier = Modifier.padding(10.dp), thickness = 2.dp)
        LazyColumn {
            items(items = notes) { note ->
                NoteCard(note = note,
                    onDeleteClick = {
                        onRemoveNote(note)
                    },
                    onNoteClick = { onNoteClick(note.id.toString())}
                    )

            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    NoteContent(
        notes = NotesDataSource.loadNote(),
        title = "",
        description = "",
        onTitleChange = {},
        onDescriptionChange = {},
        onAddNote = {},
        onRemoveNote = {},
        onNoteClick = {}
    )
}
