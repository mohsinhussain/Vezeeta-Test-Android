
package com.mohsin.vezeeta.features.characters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mohsin.vezeeta.core.platform.BaseActivity

class CharacterDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_CHARACTER = "com.mohsin.INTENT_PARAM_CHARACTER"

        fun callingIntent(context: Context, character: CharacterView): Intent {
            val intent = Intent(context, CharacterDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_CHARACTER, character)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun fragment() = CharacterDetailsFragment.forMovie(intent.getParcelableExtra(INTENT_EXTRA_PARAM_CHARACTER))
}
