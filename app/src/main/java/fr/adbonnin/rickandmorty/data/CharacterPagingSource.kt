package fr.adbonnin.rickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import fr.adbonnin.rickandmorty.model.Character
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(private val client: CharacterRepository) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageNumber = params.key ?: CharactersRepository.DEFAULT_PAGE_INDEX
        return try {
            val page = client.findByPage(pageNumber)
            LoadResult.Page(page.results, page.info.prev, page.info.next)
        }
        catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
        catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(1) ?: closestPageToPosition?.nextKey?.minus(1)
        }
    }
}