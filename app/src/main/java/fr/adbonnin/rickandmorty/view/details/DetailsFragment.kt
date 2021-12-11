package fr.adbonnin.rickandmorty.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.adbonnin.rickandmorty.App
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.data.CharacterRepository
import fr.adbonnin.rickandmorty.view.details.DetailsFragment.OnCharacterErrorListener
import kotlinx.coroutines.*

class DetailsFragment : Fragment() {

    var characterErrorListener = OnCharacterErrorListener { }

    private lateinit var name: TextView
    private lateinit var image: ImageView

    private lateinit var coroutineJob: Job

    companion object {
        fun newInstance() = DetailsFragment()
    }

    fun interface OnCharacterErrorListener {
        fun onCharacterError(message: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        name = view.findViewById(R.id.name)
        image = view.findViewById(R.id.image)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = activity?.intent
        val characterId = if (intent?.hasExtra(App.EXTRA_CHARACTER_ID) == true) {
            intent.getStringExtra(App.EXTRA_CHARACTER_ID)
        }
        else {
            null
        }

        updateDetail(characterId)
    }

    fun updateDetail(characterId: String?) {

        if (characterId == null) {
            characterErrorListener.onCharacterError(getString(R.string.error_require_character_id))
            return
        }

        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            val character = CharacterRepository().getCharacterDetails(characterId)

            withContext(Dispatchers.Main) {
                if (character == null) {
                    characterErrorListener.onCharacterError(getString(R.string.error_character_not_found))
                }
                else {
                    Picasso.get()
                        .load(character.image)
                        .into(image)

                    name.text = character.name
                }
            }
        }
    }
}