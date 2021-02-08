package gap.com.githubrepos.repository

import gap.com.githubrepos.network.GitHubApi
import javax.inject.Inject

class ReposRepository @Inject constructor(private val api: GitHubApi) : BaseRepository(){

    suspend fun getRepos(username: String, page: Int, per_page: Int) = safeCallApi {
        api.getRepositories(username, page, per_page)
    }
}