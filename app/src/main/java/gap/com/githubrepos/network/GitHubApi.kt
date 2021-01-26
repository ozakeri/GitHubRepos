package gap.com.githubrepos.network

import gap.com.githubrepos.entitiy.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("/search/users")
    suspend fun checkUserIsValid(@Query("q") username: String): SearchResponse
}