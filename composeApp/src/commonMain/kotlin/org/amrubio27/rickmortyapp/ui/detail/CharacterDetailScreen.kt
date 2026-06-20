package org.amrubio27.rickmortyapp.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.amrubio27.rickmortyapp.domain.model.CharacterModel
import org.amrubio27.rickmortyapp.ui.core.ex.aliveBorder
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
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(topStartPercent = 10, topEndPercent = 10))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                characterModel.name,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Species: ${characterModel.species}",
                color = Color.Black
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Box(contentAlignment = Alignment.TopCenter) {
                Box(
                    modifier = Modifier
                        .size(205.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = characterModel.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(190.dp)
                            .clip(CircleShape)
                            .aliveBorder(characterModel.isAlive),
                        contentScale = ContentScale.Crop
                    )
                }

                val aliveCopy = if (characterModel.isAlive) "ALIVE" else "DEAD"
                val aliveColor = if (characterModel.isAlive) Color.Green else Color.Red
                Text(
                    aliveCopy,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(38))
                        .background(aliveColor)
                        .padding(
                            horizontal = 6.dp,
                            vertical = 2.dp
                        )
                )
            }
            Spacer(Modifier.weight(1f))
        }
    }
}