package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.core.extension.empty

data class Thumbnail (val path: String, val extension: String) {

    companion object {
        fun empty() = Thumbnail(String.empty(), String.empty())
    }

}
