package org.amrubio27.rickmortyapp.data.remote.response

import kotlinx.serialization.Serializable
import org.amrubio27.rickmortyapp.domain.model.EpisodeModel
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode.SEASON_1
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode.SEASON_2
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode.SEASON_3
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode.SEASON_4
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode.SEASON_5
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode.SEASON_6
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode.SEASON_7
import org.amrubio27.rickmortyapp.domain.model.SeasonEpisode.UNKNOWN

@Serializable
data class EpisodeResponse(
    val id: Int,
    val name: String,
    val episode: String,
    val characters: List<String>
) {
    fun toDomain(): EpisodeModel {
        val season = getSeasonFromEpisodeCode(episode)
        return EpisodeModel(
            id = id,
            name = name,
            episode = episode,
            characters = characters.map { url -> url.substringAfter("/") },
            season = season,
            videoUrl = getVideoUrlFromSeason(season)
        )
    }

    private fun getVideoUrlFromSeason(season: SeasonEpisode): String {
        return when (season) {
            SEASON_1 -> "https://www.youtube.com/watch?v=8BEzj2kRjO8&ab_channel=RottenTomatoesTV"
            SEASON_2 -> "https://www.youtube.com/watch?v=SXwf_9xJu5c&ab_channel=Yusuto"
            SEASON_3 -> "https://www.youtube.com/watch?v=Bmg2vXOQ3kM&ab_channel=SeriesTrailerMP"
            SEASON_4 -> "https://www.youtube.com/watch?v=bLI2-v264No&ab_channel=RottenTomatoesTV"
            SEASON_5 -> "https://www.youtube.com/watch?v=yC1UxW8vcDo&ab_channel=RottenTomatoesTV"
            SEASON_6 -> "https://www.youtube.com/watch?v=jerFRSQW9g8&ab_channel=RottenTomatoesTV"
            SEASON_7 -> "https://www.youtube.com/watch?v=PkZtVBNkmso&ab_channel=RottenTomatoesTV"
            UNKNOWN -> "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        }
    }

    private fun getSeasonFromEpisodeCode(episode: String): SeasonEpisode {
        return when {
            episode.startsWith("S01") -> SEASON_1
            episode.startsWith("S02") -> SEASON_2
            episode.startsWith("S04") -> SEASON_4
            episode.startsWith("S05") -> SEASON_5
            episode.startsWith("S06") -> SEASON_6
            episode.startsWith("S07") -> SEASON_7
            else -> UNKNOWN
        }
    }
}