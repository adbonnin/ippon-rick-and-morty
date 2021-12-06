package fr.adbonnin.rickandmorty.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.api.Character

class ListAdapter(
    private val characters: List<Character>,
    private val selectCharacterListener: OnSelectCharacterListener
) : RecyclerView.Adapter<ListAdapter.ViewHolder>(), View.OnClickListener {

    fun interface OnSelectCharacterListener {
        fun onSelectCharacter(character: Character)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.card_view)!!
        val name = itemView.findViewById<TextView>(R.id.name)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]

        with(holder) {
            cardView.tag = character
            cardView.setOnClickListener(this@ListAdapter)
            name.text = character.name ?: ""
        }
    }

    override fun getItemCount(): Int = characters.size

    override fun onClick(view: View) {
        when (view.id) {
            R.id.card_view -> selectCharacterListener.onSelectCharacter(view.tag as Character)
        }
    }
}