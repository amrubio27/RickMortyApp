package org.amrubio27.rickmortyapp.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.amrubio27.rickmortyapp.domain.model.CharacterModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parameterSetOf
import rickmortyapp.composeapp.generated.resources.Res
import rickmortyapp.composeapp.generated.resources.space

@Composable
fun CharacterDetailScreen(characterModel: CharacterModel) {
    val characterDetailViewModel =
        koinViewModel<CharacterDetailViewModel>(parameters = { parameterSetOf(characterModel) })
    val state by characterDetailViewModel.uiState.collectAsState()

    MainHeader(characterModel)
}

@Composable
fun MainHeader(characterModel: CharacterModel) {
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.space),
                contentDescription = "Background Header",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            CharacterHeader(characterModel)
        }

    }
}

@Composable
fun CharacterHeader(
    characterModel: CharacterModel
) {
    //TODO
}