package com.example.eznote.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.eznote.R
import com.example.eznote.model.Note
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NoteCard(modifier: Modifier = Modifier, note: Note, onDeleteClick: () -> Unit = {}, onNoteClick: () -> Unit = {}) {
    Surface(modifier = modifier.padding(horizontal = 22.dp, vertical = 5.dp)
        .clip(RoundedCornerShape(7.dp))
        .fillMaxWidth()
        .clickable { onNoteClick() },
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = 4.dp) {
        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp).weight(1f),
                horizontalAlignment = Alignment.Start) {
                Text(text = note.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(text = note.description, style = MaterialTheme.typography.bodyMedium)
                val formatter = DateTimeFormatter.ofPattern("EEE, d MMM", Locale.getDefault())
                    .withZone(ZoneId.systemDefault())
                Text(
                    text = formatter.format(note.entryDate)
                        .split(" ")
                        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(onClick = onDeleteClick,
                modifier = Modifier.padding(end = 8.dp)) {
                Icon(imageVector = Icons.Rounded.Delete,
                    contentDescription = stringResource(id = R.string.delete_icon),
                    tint = MaterialTheme.colorScheme.error
                    )
            }
        }

    }
}