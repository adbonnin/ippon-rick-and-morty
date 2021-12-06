package fr.adbonnin.rickandmorty.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.databinding.ItemListBinding
import fr.adbonnin.rickandmorty.model.Character

class CharactersAdapter(
    private val selectCharacterListener: OnSelectCharacterListener
) : PagingDataAdapter<Character, CharacterViewHolder>(DIFF_CALLBACK), View.OnClickListener {

    fun interface OnSelectCharacterListener {
        fun onSelectCharacter(character: Character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        if (character != null) {
            holder.bind(character, this)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.card_view -> selectCharacterListener.onSelectCharacter(view.tag as Character)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }
}

class CharacterViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character?, listener: View.OnClickListener) {
        binding.name.text = character?.name ?: ""
        binding.cardView.tag = character
        binding.cardView.setOnClickListener(listener)
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            val binding = ItemListBinding.bind(view)
            return CharacterViewHolder(binding)
        }
    }
}