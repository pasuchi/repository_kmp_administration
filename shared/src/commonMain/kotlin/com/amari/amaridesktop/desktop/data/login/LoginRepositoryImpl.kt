package com.amari.amaridesktop.desktop.data.login

import com.amari.amaridesktop.desktop.data.login.remote.LoginApi
import com.amari.amaridesktop.desktop.data.login.remote.request.LoginRequest
import com.amari.amaridesktop.desktop.domain.login.model.Login
import com.amari.amaridesktop.desktop.domain.login.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(private val api: LoginApi) : LoginRepository {
    override fun login(
        user: String,
        password: String
    ): Flow<Result<Login>> = flow {
        val result = api.callLogin(LoginRequest(id = user, password = password))
        result.map {
            Login(message = it.message, userId = it.userId, email = it.email, isAdmin = it.isAdmin)
        }.onSuccess {login ->
            emit(Result.success(login))
        }.onFailure {
            emit(Result.failure(it))
        }


    }

}