
package com.mohsin.vezeeta.core.navigation


import android.content.Context
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import com.mohsin.vezeeta.features.characters.CharacterDetailsActivity
import com.mohsin.vezeeta.features.characters.CharacterView
import com.mohsin.vezeeta.features.characters.CharactersActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor()
  {

    fun showMain(context: Context) {
        showMovies(context)
    }

    private fun showMovies(context: Context) = context.startActivity(CharactersActivity.callingIntent(context))

    fun showMovieDetails(activity: FragmentActivity, character: CharacterView, navigationExtras: Extras) {
        val intent = CharacterDetailsActivity.callingIntent(activity, character)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)
}


