package gap.com.githubrepos.repository

import gap.com.githubrepos.network.GitHubApi
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: GitHubApi) : BaseRepository() {

    suspend fun getSearch(username: String, page: Int, per_page: Int) = safeCallApi {
        api.searchUser(username, page, per_page)
    }
}