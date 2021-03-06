package fr.adbonnin.rickandmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import fr.adbonnin.rickandmorty.api.GetCharacterDetailsQuery
import fr.adbonnin.rickandmorty.api.GetCharactersListQuery
import fr.adbonnin.rickandmorty.api.RickAndMortyApi
import fr.adbonnin.rickandmorty.api.fragment.CharacterDetails
import fr.adbonnin.rickandmorty.api.fragment.CharacterItem
import fr.adbonnin.rickandmorty.api.fragment.CharactersList
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val rickAndMortyApiClient: ApolloClient = RickAndMortyApi.create()
) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = CharacterRepository()
    }

    suspend fun getCharactersList(page: Int, filter: GetCharactersListFilter): CharactersList? {
        val gender = filter.gender.value
        val status = filter.status.value
        val response = rickAndMortyApiClient.query(GetCharactersListQuery(page, Input.fromNullable(gender), Input.fromNullable(status))).await()
        return response.data?.characters?.fragments?.charactersList
    }

    suspend fun getCharacterDetails(id: String): CharacterDetails? {
        val response = rickAndMortyApiClient.query(GetCharacterDetailsQuery(id)).await()
        return response.data?.character?.fragments?.characterDetails
    }

    fun getCharactersFlow(filter: GetCharactersListFilter, pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<CharacterItem>> {
        return Pager(pagingConfig, null, { GetCharactersPagingSource(this, filter) }).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }
}