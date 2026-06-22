package com.example.eznote.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.eznote.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreenAppBar() {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.app_name), color = MaterialTheme.colorScheme.onBackground)
    },
        actions = {
            Icon(imageVector = Icons.Rounded.Notifications, contentDescription = stringResource(id = R.string.icon_notification), tint = MaterialTheme.colorScheme.onBackground)
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
        )
}