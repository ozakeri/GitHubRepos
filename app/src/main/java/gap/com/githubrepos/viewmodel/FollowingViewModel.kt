package gap.com.githubrepos.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.android.githubservice.entity.follower_following.FollowerFollowingResponse
import gap.com.githubrepos.repository.FollowingRepository
import gap.com.githubrepos.utils.Resource
import kotlinx.coroutines.launch

class FollowingViewModel @ViewModelInject constructor(private val followingRepository: FollowingRepository) :
    ViewModel() {

    private var _followingResponse: MutableLiveData<Resource<FollowerFollowingResponse>> =
        MutableLiveData()
    val followingResponse: LiveData<Resource<FollowerFollowingResponse>>
        get() = _followingResponse

    fun following(username: String, page: Int, per_page: Int) = viewModelScope.launch {
        _followingResponse.value = followingRepository.getFollowing(username, page, per_page)
    }
}