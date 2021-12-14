package fr.adbonnin.rickandmorty.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.api.fragment.CharacterItem
import fr.adbonnin.rickandmorty.databinding.ItemCharacterBinding

class CharacterAdapter(
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
            R.id.item_view -> selectCharacterListener.onSelectCharacter(view.tag as CharacterItem)
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
        val subtitle = arrayListOf(character?.status, character?.species).asSequence()
            .filterNotNull()
            .joinToString(" - ")

        binding.nameText.text = character?.name ?: ""
        binding.subtitleText.text = subtitle
        binding.itemView.tag = character
        binding.itemView.setOnClickListener(listener)

        Picasso.get()
            .load(character?.image)
            .placeholder(R.drawable.im_character_placeholder)
            .into(binding.image)
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
            val binding = ItemCharacterBinding.bind(view)
            return CharacterViewHolder(binding)
        }
    }
}