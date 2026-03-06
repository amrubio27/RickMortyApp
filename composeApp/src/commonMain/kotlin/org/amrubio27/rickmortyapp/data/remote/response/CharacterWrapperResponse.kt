package org.amrubio27.rickmortyapp.data.remote.response

data class CharacterWrapperResponse(
    val info: InfoResponse,
    val results: List<CharacterResponse>
)
