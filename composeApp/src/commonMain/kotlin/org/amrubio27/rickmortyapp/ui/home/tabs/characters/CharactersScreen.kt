package org.amrubio27.rickmortyapp.ui.home.tabs.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.amrubio27.rickmortyapp.domain.model.CharacterModel
import org.amrubio27.rickmortyapp.ui.core.ex.vertical
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersScreen() {
    val charactersViewModels = koinViewModel<CharactersViewModel>()
    val state by charactersViewModels.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
    ) {
        state.characterOfTheDay?.let {
            CharacterOfTheDay(state.characterOfTheDay)
        }
    }
}

@Preview
@Composable
fun CharacterOfTheDay(characterModel: CharacterModel? = null) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth().height(400.dp),
            shape = RoundedCornerShape(12)
        ) {
            if (characterModel == null) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Box(
                    contentAlignment = Alignment.BottomStart
                ) {
                    Box(Modifier.fillMaxSize().background(Color.Green.copy(alpha = 0.5f)))
                    AsyncImage(
                        model = characterModel.image,
                        contentDescription = "Character of the day",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        Modifier.fillMaxSize().background(
                            Brush.horizontalGradient(
                                0f to Color.Black.copy(alpha = 0.5f),
                                0.4f to Color.White.copy(alpha = 0f),
                            )
                        )
                    )
                    Text(
                        characterModel.name,
                        fontSize = 40.sp,
                        maxLines = 1,
                        minLines = 1,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(
                                horizontal = 24.dp,
                                vertical = 16.dp
                            ).vertical()
                            .rotate(-90f)
                    )
                }
            }
        }
    }
}
