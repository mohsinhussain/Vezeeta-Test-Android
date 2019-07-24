package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.core.extension.empty

data class Thmbnail (val path: String, val extension: String) {

    companion object {
        fun empty() = Thmbnail(String.empty(), String.empty())
    }

}
