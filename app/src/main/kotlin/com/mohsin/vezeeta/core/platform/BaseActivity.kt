
package com.mohsin.vezeeta.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohsin.vezeeta.R.id
import com.mohsin.vezeeta.R.layout
import com.mohsin.vezeeta.core.extension.inTransaction
import kotlinx.android.synthetic.main.toolbar.toolbar


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_layout)
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
                id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) =
            savedInstanceState ?: supportFragmentManager.inTransaction { add(
                    id.fragmentContainer, fragment()) }

    abstract fun fragment(): BaseFragment
}
