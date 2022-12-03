package com.kuluruvineeth.socialnetwork.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuluruvineeth.socialnetwork.core.domain.states.PasswordTextFieldState
import com.kuluruvineeth.socialnetwork.core.domain.states.StandardTextFieldState
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.Screen
import com.kuluruvineeth.socialnetwork.core.util.UiText
import com.kuluruvineeth.socialnetwork.feature_auth.domain.use_case.LoginUseCase
import com.kuluruvineeth.socialnetwork.feature_auth.presentation.register.RegisterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email
                )
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.password
                )
            }
            is LoginEvent.TogglePassswordVisibility -> {
                _loginState.value = loginState.value.copy(
                    isPasswordVisible = !loginState.value.isPasswordVisible
                )
            }
            is LoginEvent.Login -> {
                _loginState.value = loginState.value.copy(
                    isLoading = true
                )
                viewModelScope.launch {
                    val loginResult = loginUseCase(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )
                    _loginState.value = loginState.value.copy(
                        isLoading = false
                    )
                    if(loginResult.emailError != null){
                        _emailState.value = emailState.value.copy(
                            error = loginResult.emailError
                        )
                    }
                    if(loginResult.passwordError != null){
                        _passwordState.value = passwordState.value.copy(
                            error = loginResult.passwordError
                        )
                    }
                    when(loginResult.result){
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.Navigate(Screen.MainFeedScreen.route)
                            )
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = loginResult.result.uiText ?: UiText.unknownError()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}