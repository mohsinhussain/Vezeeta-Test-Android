
package com.mohsin.vezeeta.features.characters

import androidx.fragment.app.FragmentActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject


class CharacterDetailAnimator
@Inject constructor() {

    private val TRANSITION_DELAY = 200L
    private val TRANSITION_DURATION = 400L

    private val SCALE_UP_VALUE = 1.0F
    private val SCALE_UP_DURATION = 400L

    internal fun postponeEnterTransition(activity: FragmentActivity) = activity.postponeEnterTransition()

    internal fun scaleUpView(view: View) = scaleView(view, SCALE_UP_VALUE, SCALE_UP_VALUE, SCALE_UP_DURATION)

    internal fun fadeVisible(viewContainer: ViewGroup, view: View) = beginTransitionFor(viewContainer, view, View.VISIBLE)
    internal fun fadeInvisible(viewContainer: ViewGroup, view: View) = beginTransitionFor(viewContainer, view, View.INVISIBLE)

    private fun scaleView(view: View, x: Float, y: Float, duration: Long) =
            view.animate()
                    .scaleX(x)
                    .scaleY(y)
                    .setDuration(duration)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .withLayer()
                    .setListener(null)
                    .start()

    private fun beginTransitionFor(viewContainer: ViewGroup, view: View, visibility: Int) {
        val transition = Fade()
        transition.startDelay = TRANSITION_DELAY
        transition.duration = TRANSITION_DURATION
        TransitionManager.beginDelayedTransition(viewContainer, transition)
        view.visibility = visibility
    }
}


