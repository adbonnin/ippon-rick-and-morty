package fr.adbonnin.rickandmorty.api

import fr.adbonnin.rickandmorty.model.Character

data class ApiResponse(
    val data: Data = Data(),
) {
    data class Data(
        val character: Character? = null,
        val characters: Page<Character>? = null,
    )
}