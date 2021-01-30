package gap.com.githubrepos.di

import com.faramarzaf.sdk.af_android_sdk.core.network.ServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gap.com.githubrepos.network.GitHubApi
import gap.com.githubrepos.repository.AuthRepository
import gap.com.githubrepos.utils.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(api: GitHubApi) = AuthRepository(api)

    @Singleton
    @Provides
    fun provideGitHubApi(): GitHubApi {
        val api by lazy { ServiceRepository.ServiceBuilder.buildService(BASE_URL, GitHubApi::class.java) }
        return api
    }
}