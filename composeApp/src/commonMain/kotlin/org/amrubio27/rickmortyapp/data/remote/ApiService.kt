package org.amrubio27.rickmortyapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.amrubio27.rickmortyapp.data.remote.response.CharacterResponse
import org.amrubio27.rickmortyapp.data.remote.response.CharacterWrapperResponse
import org.amrubio27.rickmortyapp.data.remote.response.EpisodeResponse
import org.amrubio27.rickmortyapp.data.remote.response.EpisodesWrapperResponse

class ApiService(private val client: HttpClient) {
    suspend fun getSingleCharacter(id: String): CharacterResponse {
        return client.get("/api/character/$id").body()
    }

    suspend fun getAllCharacters(page: Int): CharacterWrapperResponse {
        return client.get("/api/character/") {
            parameter("page", page)
        }.body()
    }

    suspend fun getAllEpisodes(page: Int): EpisodesWrapperResponse {
        return client.get(urlString = "/api/episode") {
            parameter("page", page)
        }.body()
    }

    suspend fun getEpisodes(episodes: String): List<EpisodeResponse> {
        return client.get("/api/episode/$episodes").body()
    }

    suspend fun getSingleEpisode(episodeId: String): EpisodeResponse {
        return client.get("/api/episode/$episodeId").body()
    }
}