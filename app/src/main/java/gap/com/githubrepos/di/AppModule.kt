package gap.com.githubrepos.di

import android.content.Context
import androidx.room.Room
import com.faramarzaf.sdk.af_android_sdk.core.network.ServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import gap.com.githubrepos.db.GitHubDatabase
import gap.com.githubrepos.network.GitHubApi
import gap.com.githubrepos.repository.*
import gap.com.githubrepos.utils.BASE_URL
import gap.com.githubrepos.utils.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGitHubDatabase(gitHubDatabase: GitHubDatabase) = gitHubDatabase.getGitHubDao()

    @Singleton
    @Provides
    fun provideGitHubDao(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, GitHubDatabase::class.java,
        DATABASE_NAME
    ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideAuthRepository(api: GitHubApi) = AuthRepository(api)

    @Singleton
    @Provides
    fun provideFollowerRepository(api: GitHubApi) = FollowerRepository(api)

    @Singleton
    @Provides
    fun provideFollowingRepository(api: GitHubApi) = FollowingRepository(api)

    @Singleton
    @Provides
    fun provideReposRepository(api: GitHubApi) = ReposRepository(api)

    @Singleton
    @Provides
    fun provideStarRepository(api: GitHubApi) = StaredRepository(api)

    @Singleton
    @Provides
    fun provideSearchRepository(gitHubApi: GitHubApi, gitHubDatabase: GitHubDatabase) =
        SearchRepository(gitHubApi, gitHubDatabase)

    @Singleton
    @Provides
    fun provideGitHubApi(): GitHubApi {
        val api by lazy {
            ServiceRepository.ServiceBuilder.buildService(
                BASE_URL,
                GitHubApi::class.java
            )
        }
        return api
    }
}