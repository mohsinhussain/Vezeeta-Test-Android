/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

class DialogAdapter
@Inject constructor() : RecyclerView.Adapter<DialogAdapter.ViewHolder>() {

    internal var collection: ArrayList<ResourceEntity> by Delegates.observable(ArrayList<ResourceEntity>()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (CharacterView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.row_dialog))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieView: ResourceEntity, clickListener: (CharacterView, Navigator.Extras) -> Unit) {
            itemView.moviePoster.loadFromUrl(movieView.thumbnail!!.path+"."+movieView.thumbnail!!.extension)
//            itemView.setOnClickListener { clickListener(movieView, Navigator.Extras(itemView.moviePoster)) }
        }
    }
}