
package com.mohsin.vezeeta.core.platform

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohsin.vezeeta.AndroidApplication
import com.mohsin.vezeeta.R.color
import com.mohsin.vezeeta.core.di.ApplicationComponent
import com.mohsin.vezeeta.core.extension.appContext
import com.mohsin.vezeeta.core.extension.viewContainer
import kotlinx.android.synthetic.main.toolbar.progress
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
            with(activity) { if (this is BaseActivity) this.progress.visibility = viewStatus }

    internal fun notify(@StringRes message: Int) =
            Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(ContextCompat.getColor(appContext,
                color.colorTextPrimary))
        snackBar.show()
    }
}
