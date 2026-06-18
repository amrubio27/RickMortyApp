package org.amrubio27.rickmortyapp.ui.core.navigation

import kotlinx.serialization.Serializable

sealed class Routes(val route: String) {
    data object Home : Routes("home")

    // BottomNav
    data object Characters : Routes("characters")
    data object Episodes : Routes("episodes")
}

@Serializable
data class CharacterDetail(val characterModel: String)
