package gap.com.githubrepos.network

import app.android.githubservice.entity.follower_following.FollowerFollowingResponse
import app.android.githubservice.entity.starred.StarredResponse
import gap.com.githubrepos.entitiy.SearchResponse
import gap.com.githubrepos.entitiy.repo.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("/search/users")
    suspend fun checkUserIsValid(@Query("q") username: String): SearchResponse

    @GET("/users/{user}/repos")
    suspend fun getRepositories(
        @Path("user") username: String
        , @Query("page") page: Int
        , @Query("per_page") per_page: Int
    ): RepositoryResponse

    @GET("/users/{user}/followers")
    suspend fun getFollowers(
        @Path("user") username: String
        , @Query("page") page: Int
        , @Query("per_page") per_page: Int
    ): FollowerFollowingResponse


    @GET("/users/{user}/following")
    suspend fun getFollowing(
        @Path("user") username: String
        , @Query("page") page: Int
        , @Query("per_page") per_page: Int
    ): FollowerFollowingResponse


    @GET("/users/{user}/starred")
    suspend fun getStarredRepositories(
        @Path("user") username: String
        , @Query("page") page: Int
        , @Query("per_page") per_page: Int
    ): StarredResponse

    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") username: String
        , @Query("page") page: Int
        , @Query("per_page") per_page: Int
    ): SearchResponse
}