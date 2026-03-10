package org.amrubio27.rickmortyapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.amrubio27.rickmortyapp.ui.core.navigation.NavigationWrapper

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationWrapper()
    }
}