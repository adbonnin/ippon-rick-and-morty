package fr.adbonnin.rickandmorty.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.api.fragment.CharacterItem
import fr.adbonnin.rickandmorty.databinding.ItemCharacterBinding

class CharactersAdapter(
    private val selectCharacterListener: OnSelectCharacterListener
) : PagingDataAdapter<CharacterItem, CharacterViewHolder>(DIFF_CALLBACK), View.OnClickListener {

    fun interface OnSelectCharacterListener {
        fun onSelectCharacter(character: CharacterItem)
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
            R.id.card_view -> selectCharacterListener.onSelectCharacter(view.tag as CharacterItem)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharacterItem>() {
            override fun areItemsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean =
                oldItem == newItem
        }
    }
}

class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: CharacterItem?, listener: View.OnClickListener) {
        binding.name.text = character?.name ?: ""
        binding.cardView.tag = character
        binding.cardView.setOnClickListener(listener)
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
            val binding = ItemCharacterBinding.bind(view)
            return CharacterViewHolder(binding)
        }
    }
}