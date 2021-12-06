package fr.adbonnin.rickandmorty.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.adbonnin.rickandmorty.App
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.api.Character
import fr.adbonnin.rickandmorty.ui.detail.DetailActivity

private const val TAG = "ListActivity"

class ListActivity : AppCompatActivity() {

    private lateinit var listFragment: ListFragment

    private var currentCharacter: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listFragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as ListFragment
        listFragment.selectCharacterListener = ListAdapter.OnSelectCharacterListener(::onSelectCharacter)
    }

    private fun onSelectCharacter(character: Character) {
        Log.i(TAG, "onSelectCharacter; character: $character")

        currentCharacter = character
        startDetailActivity(character)
    }

    private fun startDetailActivity(character: Character) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(App.EXTRA_CHARACTER_ID, character.id)
        startActivity(intent)
    }
}