package org.amrubio27.rickmortyapp.ui.core.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import org.amrubio27.rickmortyapp.ui.core.DefaultTextColor

@Composable
fun TextTitle(text: String) {
    Text(text.uppercase(), color = DefaultTextColor, fontWeight = FontWeight.Bold)
}