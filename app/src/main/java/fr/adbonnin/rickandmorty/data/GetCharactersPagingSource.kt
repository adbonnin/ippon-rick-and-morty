package fr.adbonnin.rickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import fr.adbonnin.rickandmorty.api.fragment.CharacterItem
import retrofit2.HttpException
import java.io.IOException

class GetCharactersPagingSource(
    private val repository: CharacterRepository,
    private val filter: GetCharactersFilter,
) : PagingSource<Int, CharacterItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> {
        val pageNumber = params.key ?: CharacterRepository.DEFAULT_PAGE_INDEX
        return try {
            val characters = repository.getCharacters(pageNumber)
            val info = characters?.info
            val result = characters?.results?.mapNotNull { it?.fragments?.characterItem } ?: emptyList()
            LoadResult.Page(result, info?.prev, info?.next)
        }
        catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
        catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterItem>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(1) ?: closestPageToPosition?.nextKey?.minus(1)
        }
    }
}