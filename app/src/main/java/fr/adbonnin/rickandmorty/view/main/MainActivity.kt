package fr.adbonnin.rickandmorty.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.adbonnin.rickandmorty.App
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.api.fragment.CharacterItem
import fr.adbonnin.rickandmorty.view.details.DetailsFragment
import fr.adbonnin.rickandmorty.view.details.DetailsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val TAG = "ListActivity"

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment
    private var detailsFragment: DetailsFragment? = null

    private var currentCharacter: CharacterItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as MainFragment
        mainFragment.selectCharacterListener = CharacterAdapter.OnSelectCharacterListener(::onSelectCharacter)

        detailsFragment = supportFragmentManager.findFragmentById(R.id.details_fragment) as? DetailsFragment
    }

    private fun onSelectCharacter(character: CharacterItem) {
        Log.i(TAG, "onSelectCharacter; character: $character")

        currentCharacter = character

        if (detailsFragment == null) {
            startDetailActivity(character)
        }
        else {
            detailsFragment?.updateDetail(character.id)
        }
    }

    private fun startDetailActivity(character: CharacterItem) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(App.EXTRA_CHARACTER_ID, character.id)
        startActivity(intent)
    }
}