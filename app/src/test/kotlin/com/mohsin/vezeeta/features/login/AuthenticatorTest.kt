
package com.mohsin.vezeeta.features.login

import com.mohsin.vezeeta.UnitTest
import org.amshove.kluent.shouldBe
import org.junit.Test

class AuthenticatorTest : UnitTest() {

    private val authenticator = Authenticator()

    @Test fun `returns default value`() {
        authenticator.userLoggedIn() shouldBe true
    }
}
