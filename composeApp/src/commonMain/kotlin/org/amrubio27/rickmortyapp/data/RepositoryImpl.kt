package org.amrubio27.rickmortyapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import org.amrubio27.rickmortyapp.data.database.RickMortyDatabase
import org.amrubio27.rickmortyapp.data.remote.ApiService
import org.amrubio27.rickmortyapp.data.remote.paging.CharactersPagingSource
import org.amrubio27.rickmortyapp.domain.Repository
import org.amrubio27.rickmortyapp.domain.model.CharacterModel

class RepositoryImpl(
    private val api: ApiService,
    private val characterPagingSource: CharactersPagingSource,
    private val database: RickMortyDatabase
) : Repository {
    companion object {
        const val MAX_SIZE = 20
        const val PREFETCH_ITEMS = 5
    }

    override suspend fun getSingleCharacter(id: String): CharacterModel {
        return api.getSingleCharacter(id).toDomain()

    }

    override fun getAllCharacters(): Flow<PagingData<CharacterModel>> {
        Logger.d { "getAllCharacters called from RepositoryImpl" }
        return Pager(
            config = PagingConfig(
                pageSize = MAX_SIZE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = { characterPagingSource }
        ).flow
    }

    override suspend fun getCharacterDB() {
        database.getPreferencesDao().getCharacterOfTheDayDB()

    }
}