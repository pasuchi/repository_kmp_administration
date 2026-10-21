package com.amari.amaridesktop.desktop.domain.login

import com.amari.amaridesktop.desktop.domain.login.model.Login
import com.amari.amaridesktop.desktop.domain.login.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUsecase(private val loginRepository: LoginRepository) {


    operator fun invoke(user: String, password: String): Flow<Result<Login>> =
        loginRepository.login(user, password)

}