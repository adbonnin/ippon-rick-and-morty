package fr.adbonnin.rickandmorty.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.adbonnin.rickandmorty.App
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.api.Character
import fr.adbonnin.rickandmorty.api.RickAndMortyService
import fr.adbonnin.rickandmorty.ui.detail.DetailFragment.OnCharacterErrorListener

private const val TAG = "DetailFragment"

private const val DEFAULT_CHARACTER_ID = -1

class DetailFragment : Fragment() {

    var characterErrorListener = OnCharacterErrorListener { }

    private lateinit var name: TextView
    private lateinit var image: ImageView

    companion object {

        fun newInstance() = DetailFragment()
    }

    fun interface OnCharacterErrorListener {
        fun onCharacterError(message: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        name = view.findViewById(R.id.name)
        image = view.findViewById(R.id.image)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = activity?.intent
        val characterId = if (intent?.hasExtra(App.EXTRA_CHARACTER_ID) == true) {
            intent.getIntExtra(App.EXTRA_CHARACTER_ID, DEFAULT_CHARACTER_ID)
        }
        else {
            DEFAULT_CHARACTER_ID
        }

        if (characterId == DEFAULT_CHARACTER_ID) {
            characterErrorListener.onCharacterError(getString(R.string.error_require_character_id))
            return
        }

        updateDetailForCharacter(characterId)
    }

    private fun updateDetailForCharacter(characterId: Int) {

        val handler = object : RickAndMortyService.ResponseHandler<Character?> {
            override fun onSuccess(value: Character?) {
                Log.d(TAG, value?.toString() ?: "<null>")
                if (value == null) {
                    characterErrorListener.onCharacterError(getString(R.string.error_character_not_found))
                    return
                }

                updateUi(value)
            }

            override fun onFailure(t: Throwable) {
                Log.e(TAG, "Could not load character", t)
                characterErrorListener.onCharacterError(getString(R.string.error_fail_load_character))
            }
        }

        App.rickAndMortyService.findById(characterId, handler)
    }

    private fun updateUi(character: Character) {
        Picasso.get()
            .load(character.image)
            .into(image)

        name.text = character.name
    }
}