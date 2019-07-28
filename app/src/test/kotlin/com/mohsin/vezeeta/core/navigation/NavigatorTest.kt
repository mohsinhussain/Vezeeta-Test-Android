
package com.mohsin.vezeeta.core.navigation

import com.mohsin.vezeeta.AndroidTest
import com.mohsin.vezeeta.features.login.Authenticator
import com.mohsin.vezeeta.features.login.LoginActivity
import com.mohsin.vezeeta.features.characters.CharactersActivity
import com.mohsin.vezeeta.shouldNavigateTo
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify


class NavigatorTest : AndroidTest() {

    private lateinit var navigator: Navigator

    @Mock private lateinit var authenticator: Authenticator

    @Before fun setup() {
        navigator = Navigator(authenticator)
    }

    @Test fun `should forward user to login screen`() {
        whenever(authenticator.userLoggedIn()).thenReturn(false)

        navigator.showMain(activityContext())

        verify(authenticator).userLoggedIn()
        RouteActivity::class shouldNavigateTo LoginActivity::class
    }

    @Test fun `should forward user to movies screen`() {
        whenever(authenticator.userLoggedIn()).thenReturn(true)

        navigator.showMain(activityContext())

        verify(authenticator).userLoggedIn()
        RouteActivity::class shouldNavigateTo CharactersActivity::class
    }
}
