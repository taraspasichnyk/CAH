package com.eleks.cah.data.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.github.aakira.napier.Napier

class FirebaseAuth {
    private val auth by lazy {
        Firebase.auth
    }

    suspend fun signInSilently() {
        if (auth.currentUser == null) {
            auth.signInAnonymously().user?.let {
                Napier.d(
                    tag = TAG,
                    message = "Silent sign in SUCCEED"
                )
            }
        }
    }

    companion object {
        private val TAG = FirebaseAuth::class.simpleName
    }
}