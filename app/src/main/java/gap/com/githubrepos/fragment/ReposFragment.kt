package gap.com.githubrepos.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.faramarzaf.sdk.af_android_sdk.core.util.MyPreferences
import dagger.hilt.android.AndroidEntryPoint
import gap.com.githubrepos.R
import gap.com.githubrepos.adapter.ReposAdapter
import gap.com.githubrepos.utils.*
import gap.com.githubrepos.viewmodel.FollowerViewModel
import gap.com.githubrepos.viewmodel.FollowingViewModel
import gap.com.githubrepos.viewmodel.RepositoriesViewModel
import kotlinx.android.synthetic.main.fragment_repos.*


@AndroidEntryPoint
class ReposFragment : BaseFragment() {

    private lateinit var reposAdapter: ReposAdapter
    private val viewModel: RepositoriesViewModel by viewModels()
    private val viewModelFollowers: FollowerViewModel by viewModels()
    private val viewModelFollowing: FollowingViewModel by viewModels()

    override val getFragmentLayout: Int
        get() = R.layout.fragment_repos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        getRepos()
        getFollowers()
        getFollowing()
        observeRepository()
        observeFollowers()
        observeFollowing()
        reposAdapter.setOnItemClickListener {
            toast(it.name)
        }
    }

    fun setupRecyclerView() {
        reposAdapter = ReposAdapter()
        rv_repos.apply {
            setRecyclerviewDivider(context, this, R.drawable.divider_list)
            adapter = reposAdapter
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(CACHE_SIZE)
        }
    }

    fun getRepos() {
        viewModel.getRepos(
            MyPreferences.readString(requireActivity(), KEY_USERNAME, DEFAULT_USER),
            MIN_PAGE,
            MAX_PAGE
        )
    }

    fun getFollowers() {
        viewModelFollowers.follower(
            MyPreferences.readString(requireActivity(), KEY_USERNAME, DEFAULT_USER),
            MIN_PAGE,
            MAX_PAGE
        )
    }

    fun getFollowing() {
        viewModelFollowing.following(
            MyPreferences.readString(requireActivity(), KEY_USERNAME, DEFAULT_USER),
            MIN_PAGE,
            MAX_PAGE
        )
    }

    fun observeRepository() {
        showProgressBar(reposProgressBar)
        viewModel.getReposResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    if (response.value.isEmpty()) {
                        noDataAvailable()
                    } else {
                        dataAvailable()
                    }

                    hideProgressBar(reposProgressBar)
                    reposAdapter.differ.submitList(response.value)
                    MyPreferences.writeString(
                        requireContext(),
                        KEY_SIZE_LIST_REPO,
                        response.value.size.toString()
                    )
                }
                is Resource.Failure -> {
                    hideProgressBar(reposProgressBar)
                    if (response.isNetworkError) {
                        toast("Check your connection!")
                    }
                    Log.d(TAG_LOG, "fetchRepositoryData: $response")
                }
            }
        })
    }

    fun observeFollowers() {
        viewModelFollowers.followerResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    MyPreferences.writeString(
                        requireContext(),
                        KEY_NUMBER_FOLLOWERS,
                        response.value.size.toString()
                    )
                }
                is Resource.Failure -> {
                    hideProgressBar(reposProgressBar)
                    if (response.isNetworkError) {
                        toast("Check your connection!")
                    }
                }
            }
        })
    }

    fun observeFollowing() {
        viewModelFollowing.followingResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    MyPreferences.writeString(
                        requireContext(),
                        KEY_NUMBER_FOLLOWING,
                        response.value.size.toString()
                    )
                }
                is Resource.Failure -> {
                    hideProgressBar(reposProgressBar)
                    if (response.isNetworkError) {
                        toast("Check your connection!")
                    }
                }
            }
        })
    }

    private fun noDataAvailable() {
        textNoRepos.visibility = View.VISIBLE
    }

    private fun dataAvailable() {
        textNoRepos.visibility = View.GONE
    }
}