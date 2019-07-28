
package com.mohsin.vezeeta.features.characters

import android.content.Context
import android.content.Intent
import com.mohsin.vezeeta.core.platform.BaseActivity

class CharactersActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, CharactersActivity::class.java)
    }

    override fun fragment() = CharacterListFragment()
}
