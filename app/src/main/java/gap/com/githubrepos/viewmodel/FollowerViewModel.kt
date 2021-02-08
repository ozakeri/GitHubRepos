package gap.com.githubrepos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.android.githubservice.entity.follower_following.FollowerFollowingResponse
import gap.com.githubrepos.repository.FollowerRepository
import gap.com.githubrepos.utils.Resource
import kotlinx.coroutines.launch

class FollowerViewModel @ViewModelInject constructor(private val followerRepository: FollowerRepository) :
    ViewModel() {

    private var _followResponse: MutableLiveData<Resource<FollowerFollowingResponse>> =
        MutableLiveData()
    val followerResponse: LiveData<Resource<FollowerFollowingResponse>>
        get() = _followResponse

    fun follower(username: String, page: Int, per_page: Int) = viewModelScope.launch {
        _followResponse.value = followerRepository.getFollowers(username, page, per_page)
    }
}