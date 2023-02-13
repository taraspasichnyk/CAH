package com.eleks.cah.domain.usecase.login

import com.eleks.cah.data.firebase.FirebaseAuth

class AnonymousLoginUseCaseImpl(
    private val firebaseAuth: FirebaseAuth
) : AnonymousLoginUseCase {
    override suspend fun invoke() {
        firebaseAuth.signInSilently()
    }
}