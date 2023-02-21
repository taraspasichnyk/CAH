package com.eleks.cah.domain.usecase.login

interface AnonymousLoginUseCase {
    suspend operator fun invoke()
}