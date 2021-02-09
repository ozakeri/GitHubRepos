package gap.com.githubrepos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gap.com.githubrepos.entitiy.SearchResponse
import gap.com.githubrepos.repository.SearchRepository
import gap.com.githubrepos.utils.Resource
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {

    private var _searchRepository: MutableLiveData<Resource<SearchResponse>> =
        MutableLiveData()
    val searchResponse: LiveData<Resource<SearchResponse>>
        get() = _searchRepository

    fun search(username: String, page: Int, per_page: Int) = viewModelScope.launch {
        _searchRepository.value = searchRepository.getSearch(username, page, per_page)
    }
}