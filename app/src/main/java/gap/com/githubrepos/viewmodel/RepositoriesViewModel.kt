package gap.com.githubrepos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gap.com.githubrepos.entitiy.repo.RepositoryResponse
import gap.com.githubrepos.repository.ReposRepository
import gap.com.githubrepos.utils.Resource
import kotlinx.coroutines.launch

class RepositoriesViewModel @ViewModelInject constructor(private val repository: ReposRepository) :
    ViewModel() {

    private var _getReposResponse: MutableLiveData<Resource<RepositoryResponse>> = MutableLiveData()
    val getReposResponse: LiveData<Resource<RepositoryResponse>>
        get() = _getReposResponse

    fun getRepos(username: String, page: Int, per_page: Int) = viewModelScope.launch {
        _getReposResponse.value = repository.getRepos(username, page, per_page)
    }
}