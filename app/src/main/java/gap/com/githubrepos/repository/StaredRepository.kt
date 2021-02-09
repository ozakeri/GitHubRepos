package gap.com.githubrepos.repository

import gap.com.githubrepos.network.GitHubApi
import javax.inject.Inject

class StaredRepository @Inject constructor(private val api: GitHubApi) : BaseRepository() {

    suspend fun getStared(username: String, page: Int, per_page: Int) = safeCallApi {
        api.getStarredRepositories(username, page, per_page)
    }
}