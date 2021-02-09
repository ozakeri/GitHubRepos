package gap.com.githubrepos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.android.githubservice.entity.starred.StarredResponse
import gap.com.githubrepos.repository.StaredRepository
import gap.com.githubrepos.utils.Resource
import kotlinx.coroutines.launch

class StaredViewModel @ViewModelInject constructor(private val staredRepository: StaredRepository) :
    ViewModel() {

    private var _staredRepository: MutableLiveData<Resource<StarredResponse>> =
        MutableLiveData()
    val staredResponse: LiveData<Resource<StarredResponse>>
        get() = _staredRepository

    fun stared(username: String, page: Int, per_page: Int) = viewModelScope.launch {
        _staredRepository.value = staredRepository.getStared(username, page, per_page)
    }
}