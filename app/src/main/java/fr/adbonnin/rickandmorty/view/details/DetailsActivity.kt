package fr.adbonnin.rickandmorty.view.details

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = DetailsFragment.newInstance()
        fragment.characterErrorListener = DetailsFragment.OnCharacterErrorListener(::characterErrorListener)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .commit()
    }

    private fun characterErrorListener(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }
}