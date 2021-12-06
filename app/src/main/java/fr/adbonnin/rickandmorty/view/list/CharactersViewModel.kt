package fr.adbonnin.rickandmorty.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import fr.adbonnin.rickandmorty.data.CharactersRepository
import fr.adbonnin.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow

class CharactersViewModel(
    private val repository: CharactersRepository = CharactersRepository.getInstance()
) : ViewModel() {

    fun fetchCharacters(): Flow<PagingData<Character>> {
        return repository.letCharactersFlow().cachedIn(viewModelScope)
    }
}