
package com.mohsin.vezeeta.core.exception


sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object ResourceError : Failure()

}
