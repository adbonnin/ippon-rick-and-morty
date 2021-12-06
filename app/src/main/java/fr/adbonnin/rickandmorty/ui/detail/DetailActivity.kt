package fr.adbonnin.rickandmorty.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = DetailFragment.newInstance()
        fragment.characterErrorListener = DetailFragment.OnCharacterErrorListener(::characterErrorListener)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .commit()
    }

    private fun characterErrorListener(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }
}