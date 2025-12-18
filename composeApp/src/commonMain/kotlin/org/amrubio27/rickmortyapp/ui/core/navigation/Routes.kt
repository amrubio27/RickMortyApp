package org.amrubio27.rickmortyapp.ui.core.navigation

sealed class Routes(val route: String) {
    data object Home : Routes("home")

    // BottomNav
    data object Characters : Routes("characters")
    data object Episodes : Routes("episodes")
}
