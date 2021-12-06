package fr.adbonnin.rickandmorty

import android.app.Application
import fr.adbonnin.rickandmorty.api.RickAndMortyService

class App : Application() {

    companion object {
        const val EXTRA_CHARACTER_ID = "fr.adbonnin.rickandmorty.extras.EXTRA_CHARACTER_ID"

        val rickAndMortyService = RickAndMortyService()
    }
}