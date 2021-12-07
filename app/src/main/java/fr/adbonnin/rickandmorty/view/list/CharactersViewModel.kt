package fr.adbonnin.rickandmorty.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import fr.adbonnin.rickandmorty.data.CharacterFilter
import fr.adbonnin.rickandmorty.data.CharactersRepository
import fr.adbonnin.rickandmorty.model.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class CharactersViewModel(
    private val repository: CharactersRepository = CharactersRepository.getInstance()
) : ViewModel() {

    val filter = MutableStateFlow(CharacterFilter())

    @ExperimentalCoroutinesApi
    val characters: Flow<PagingData<Character>> = filter.flatMapLatest { filter ->
        repository.letCharactersFlow(filter).cachedIn(viewModelScope)
    }
}