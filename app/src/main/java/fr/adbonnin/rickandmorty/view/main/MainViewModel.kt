package fr.adbonnin.rickandmorty.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import fr.adbonnin.rickandmorty.data.GetCharactersFilter
import fr.adbonnin.rickandmorty.data.CharactersRepository
import fr.adbonnin.rickandmorty.model.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class MainViewModel(
    private val charactersRepository: CharactersRepository = CharactersRepository.getInstance()
) : ViewModel() {

    val charactersFilter = MutableStateFlow(GetCharactersFilter())

    @ExperimentalCoroutinesApi
    val characters: Flow<PagingData<Character>> = charactersFilter.flatMapLatest { filter ->
        charactersRepository.getCharactersFlow(filter).cachedIn(viewModelScope)
    }
}