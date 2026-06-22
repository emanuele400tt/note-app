package com.example.eznote.components


import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddNodeButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    Button(onClick = onClick,
        shape = RoundedCornerShape(corner = CornerSize(7.dp)),
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text)
    }
}