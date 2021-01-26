package gap.com.githubrepos.repository

import gap.com.githubrepos.network.GitHubApi
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: GitHubApi) : BaseRepository(){

    suspend fun authUser(username: String) = safeCallApi {
        api.checkUserIsValid(username)
    }
}