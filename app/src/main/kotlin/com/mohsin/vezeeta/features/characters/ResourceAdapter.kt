
package com.mohsin.vezeeta.features.characters

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mohsin.vezeeta.R
import com.mohsin.vezeeta.core.extension.inflate
import com.mohsin.vezeeta.core.extension.loadFromUrl
import kotlinx.android.synthetic.main.row_movie.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class ResourceAdapter
@Inject constructor() : RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    internal var collection: ArrayList<ResourceEntity> by Delegates.observable(ArrayList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.row_resource))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieView: ResourceEntity, clickListener: (position: Int) -> Unit) {
            itemView.characterPoster.loadFromUrl(movieView.thumbnail!!.path+"."+movieView.thumbnail!!.extension)
            itemView.nameTextView.text = movieView.title
            itemView.setOnClickListener { clickListener(adapterPosition) }
        }
    }
}
