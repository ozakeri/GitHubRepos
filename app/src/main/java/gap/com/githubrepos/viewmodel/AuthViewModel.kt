package gap.com.githubrepos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gap.com.githubrepos.entitiy.SearchResponse
import gap.com.githubrepos.repository.AuthRepository
import gap.com.githubrepos.utils.Resource
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(private val authRepository: AuthRepository) : ViewModel(){

    private var _loginResponse: MutableLiveData<Resource<SearchResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<SearchResponse>>
        get() = _loginResponse

    fun auth(username: String) = viewModelScope.launch {
        _loginResponse.value = authRepository.authUser(username)
    }
}