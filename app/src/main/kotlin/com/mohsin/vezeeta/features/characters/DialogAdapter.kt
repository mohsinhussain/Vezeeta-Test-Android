
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

class DialogAdapter
@Inject constructor() : RecyclerView.Adapter<DialogAdapter.ViewHolder>() {

    internal var collection: ArrayList<ResourceEntity> by Delegates.observable(ArrayList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.row_dialog))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieView: ResourceEntity) {
            itemView.characterPoster.loadFromUrl(movieView.thumbnail!!.path+"."+movieView.thumbnail!!.extension)
        }
    }
}
