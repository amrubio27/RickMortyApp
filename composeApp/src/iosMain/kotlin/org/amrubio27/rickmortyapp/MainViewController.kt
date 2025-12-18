package org.amrubio27.rickmortyapp

import androidx.compose.ui.window.ComposeUIViewController
import org.amrubio27.rickmortyapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }
