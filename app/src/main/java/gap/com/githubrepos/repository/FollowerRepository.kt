package gap.com.githubrepos.repository

import gap.com.githubrepos.network.GitHubApi
import javax.inject.Inject

class FollowerRepository @Inject constructor(private val api: GitHubApi) : BaseRepository() {

    suspend fun getFollowers(username: String, page: Int, per_page: Int) = safeCallApi {
        api.getFollowers(username, page, per_page)
    }
}