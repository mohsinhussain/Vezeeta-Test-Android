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
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun fragment() = CharacterDetailsFragment.forMovie(intent.getParcelableExtra(INTENT_EXTRA_PARAM_CHARACTER))
}
