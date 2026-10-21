package com.amari.amaridesktop.desktop.domain.login.repository

import com.amari.amaridesktop.desktop.domain.login.model.Login
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun login(user: String, password: String): Flow<Result<Login>>
}