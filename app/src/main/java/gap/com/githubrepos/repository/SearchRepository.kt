package gap.com.githubrepos.repository

import gap.com.githubrepos.db.GitHubDatabase
import gap.com.githubrepos.entitiy.Item
import gap.com.githubrepos.network.GitHubApi
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: GitHubApi,
    private val gitHubDatabase: GitHubDatabase
) : BaseRepository() {

    suspend fun getSearch(username: String, page: Int, per_page: Int) = safeCallApi {
        api.searchUser(username, page, per_page)
    }

    suspend fun insert(item: Item) = gitHubDatabase.getGitHubDao().insert(item)

    suspend fun delete(item: Item) = gitHubDatabase.getGitHubDao().deleteUser(item)

    suspend fun deleteAll() = gitHubDatabase.getGitHubDao().deleteAll()

    fun getAllUser() = gitHubDatabase.getGitHubDao().getAllUser()

    fun ifUserExist(user : Item) = gitHubDatabase.getGitHubDao().userExist(user.id)
}