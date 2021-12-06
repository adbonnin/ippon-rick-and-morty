package fr.adbonnin.rickandmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import fr.adbonnin.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow

class CharactersRepository(
    private val client: CharacterRepository = CharacterRepository()
) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = CharactersRepository()
    }

    fun letCharactersFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Character>> {
        return Pager(pagingConfig, null, { CharacterPagingSource(client) }).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }
}