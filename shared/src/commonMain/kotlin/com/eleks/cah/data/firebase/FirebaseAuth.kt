package com.eleks.cah.data.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class FirebaseAuth {
    private val auth = Firebase.auth

    suspend fun signInSilently() {
        if (auth.currentUser == null) {
            auth.signInAnonymously().user?.let {
                // TODO: Log via Logger
            }
        }
    }
}