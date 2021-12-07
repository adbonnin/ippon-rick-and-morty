package fr.adbonnin.rickandmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import fr.adbonnin.rickandmorty.api.ApiRequest
import fr.adbonnin.rickandmorty.api.Page
import fr.adbonnin.rickandmorty.api.RickAndMortyApiService
import fr.adbonnin.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow

class CharactersRepository(
    private val rickAndMortyApiService: RickAndMortyApiService = RickAndMortyApiService.create()
) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = CharactersRepository()
    }

    fun getCharactersFlow(filter: GetCharactersFilter, pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Character>> {
        return Pager(pagingConfig, null, { GetCharactersPagingSource(this, filter) }).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    suspend fun getCharacters(
        page: Int,
        filter: GetCharactersFilter
    ): Page<Character> {
        val query = """
            query {
              characters(page: $page) {
                info {
                  count,
                  pages,
                  next,
                  prev
                }
                results {
                  id,
                  name
                }
              }
            }
        """

        val response = rickAndMortyApiService.query(ApiRequest(query))
        return response.data.characters ?: Page()
    }

    suspend fun getCharacterById(id: Int): Character? {
        val query = """
            query {
              character(id: $id) {
                id,
                name,
                status,
                species,
                type,
                gender,
                origin {
                  id,
                  name,
                  type,
                  dimension
                },
                location {
                  id,
                  name,
                  type,
                  dimension
                },
                image,
                episode {
                  id,
                  name,
                  air_date,
                  episode
                }
              },
            }
            """

        val response = rickAndMortyApiService.query(ApiRequest(query))
        return response.data.character
    }
}