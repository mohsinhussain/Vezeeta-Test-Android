
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.AndroidTest
import com.mohsin.vezeeta.core.navigation.Navigator
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class PlayMovieTest : AndroidTest() {

    private val VIDEO_URL = "https://www.youtube.com/watch?v=fernando"

    private lateinit var playMovie: PlayMovie

    private val context = context()

    @Mock private lateinit var navigator: Navigator

    @Before fun setUp() {
        playMovie = PlayMovie(context, navigator)
    }

    @Test fun `should play movie trailer`() {
        val params = PlayMovie.Params(VIDEO_URL)

        playMovie(params)

        verify(navigator).openVideo(context, VIDEO_URL)
    }
}
