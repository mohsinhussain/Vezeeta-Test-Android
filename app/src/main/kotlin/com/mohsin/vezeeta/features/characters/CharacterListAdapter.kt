
package com.mohsin.vezeeta.features.characters

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mohsin.vezeeta.R
import com.mohsin.vezeeta.core.extension.inflate
import com.mohsin.vezeeta.core.extension.loadFromUrl
import com.mohsin.vezeeta.core.navigation.Navigator
import kotlinx.android.synthetic.main.row_movie.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class CharacterListAdapter
@Inject constructor() : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    internal var collection: ArrayList<CharacterView> by Delegates.observable(ArrayList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (CharacterView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.row_movie))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(characterView: CharacterView, clickListener: (CharacterView, Navigator.Extras) -> Unit) {
            itemView.characterPoster.loadFromUrl(characterView.thumbnail.path+"."+characterView.thumbnail.extension)
            itemView.nameTextView.text = characterView.name
            itemView.setOnClickListener { clickListener(characterView, Navigator.Extras(itemView.characterPoster)) }
        }
    }
}
