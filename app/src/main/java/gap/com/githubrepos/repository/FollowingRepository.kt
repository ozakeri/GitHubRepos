package gap.com.githubrepos.repository

import gap.com.githubrepos.network.GitHubApi
import javax.inject.Inject

class FollowingRepository @Inject constructor(private val api: GitHubApi) : BaseRepository() {

    suspend fun getFollowing(username: String, page: Int, per_page: Int) = safeCallApi {
        api.getFollowing(username, page, per_page)
    }
}