package com.amari.amaridesktop.desktop.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amari.amaridesktop.desktop.domain.login.LoginUsecase
import com.amari.amaridesktop.desktop.navigator.InicioRoot
import com.amari.amaridesktop.desktop.navigator.Navigator
import com.amari.amaridesktop.desktop.presentation.ui.login.event.LoginEvent
import com.amari.amaridesktop.desktop.presentation.ui.login.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewmodel(private val loginUsecase: LoginUsecase, private val navigator: Navigator) :
    ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun event(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                    loginUsecase.invoke(user = loginEvent.user, password = loginEvent.password)
                        .collect {
                            it.onSuccess {
                                println(it.userId)
                                navigator.navigate(InicioRoot)
                            }.onFailure {
                                println(it.cause)
                            }
                            _state.update {
                                it.copy(isLoading = false)
                            }
                        }
                }

            }
        }

    }

}