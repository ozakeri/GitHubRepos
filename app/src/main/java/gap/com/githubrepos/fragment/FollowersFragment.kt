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
import gap.com.githubrepos.adapter.FollowersFollowingAdapter
import gap.com.githubrepos.utils.*
import gap.com.githubrepos.viewmodel.FollowerViewModel
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.fragment_repos.*

@AndroidEntryPoint
class FollowersFragment : BaseFragment() {

    private lateinit var followersAdapter: FollowersFollowingAdapter
    private val viewModel: FollowerViewModel by viewModels()

    override val getFragmentLayout: Int
        get() = R.layout.fragment_followers

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFollowers()
        setupRecyclerView()
        observeFollower()
    }

    private fun getFollowers() {
        viewModel.follower(
            MyPreferences.readString(requireActivity(), KEY_USERNAME, DEFAULT_USER),
            MIN_PAGE,
            MAX_PAGE
        )
    }

    fun observeFollower() {
        showProgressBar(followersProgressBar)
        viewModel.followerResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    if (response.value.isEmpty()) {
                        noDataAvailable()
                    } else {
                        dataAvailable()
                    }

                    hideProgressBar(followersProgressBar)
                    followersAdapter.differ.submitList(response.value)
                }

                is Resource.Failure -> {
                    hideProgressBar(followersProgressBar)
                    if (response.isNetworkError) {
                        toast("Check your connection!")
                    }
                    Log.d(TAG_LOG, "fetchRepositoryData: $response")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        followersAdapter = FollowersFollowingAdapter()
        rv_followers.apply {
            setRecyclerviewDivider(context, this, R.drawable.divider_list)
            adapter = followersAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

    private fun noDataAvailable() {
        textNoFollowers.visibility = View.VISIBLE
    }

    private fun dataAvailable() {
        textNoFollowers.visibility = View.GONE
    }

}