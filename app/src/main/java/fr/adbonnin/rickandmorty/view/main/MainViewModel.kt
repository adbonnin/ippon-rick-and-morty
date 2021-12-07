package fr.adbonnin.rickandmorty.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import fr.adbonnin.rickandmorty.api.fragment.CharacterItem
import fr.adbonnin.rickandmorty.data.CharacterRepository
import fr.adbonnin.rickandmorty.data.GetCharactersFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class MainViewModel(
    private val characterRepository: CharacterRepository = CharacterRepository.getInstance()
) : ViewModel() {

    val charactersFilter = MutableStateFlow(GetCharactersFilter())

    @ExperimentalCoroutinesApi
    val characters: Flow<PagingData<CharacterItem>> = charactersFilter.flatMapLatest { filter ->
        characterRepository.getCharactersFlow(filter).cachedIn(viewModelScope)
    }
}