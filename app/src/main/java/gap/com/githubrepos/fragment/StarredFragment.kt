package gap.com.githubrepos.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.faramarzaf.sdk.af_android_sdk.core.util.MyPreferences
import dagger.hilt.android.AndroidEntryPoint
import gap.com.githubrepos.R
import gap.com.githubrepos.adapter.ReposAdapter
import gap.com.githubrepos.adapter.StarredAdapter
import gap.com.githubrepos.utils.*
import gap.com.githubrepos.viewmodel.StaredViewModel
import kotlinx.android.synthetic.main.fragment_repos.*
import kotlinx.android.synthetic.main.fragment_starred.*

@AndroidEntryPoint
class StarredFragment : BaseFragment() {

    private lateinit var starredAdapter : StarredAdapter
    private val staredViewModel:StaredViewModel by viewModels()

    override val getFragmentLayout: Int
        get() = R.layout.fragment_starred

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getStar()
        starObserve()
    }

    fun setupRecyclerView() {
        starredAdapter = StarredAdapter()
        rv_starred.apply {
            setRecyclerviewDivider(context, this, R.drawable.divider_list)
            adapter = starredAdapter
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(CACHE_SIZE)
        }
    }

    fun getStar(){
        staredViewModel.stared(
            MyPreferences.readString(requireActivity(), KEY_USERNAME, DEFAULT_USER),
            MIN_PAGE,
            MAX_PAGE
        )
    }

    fun starObserve(){
        showProgressBar()
        staredViewModel.staredResponse.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success -> {

                    hideProgressBar()
                    if (response.value.isEmpty()) {
                        noDataAvailable()
                    } else {
                        dataAvailable()
                    }
                    starredAdapter.differ.submitList(response.value)
                }

                is Resource.Failure -> {
                    hideProgressBar()
                    if (response.isNetworkError) {
                        toast("Check your connection!")
                    }
                    Log.d(TAG_LOG, "fetchStarredRepositoryData: $response")
                }
            }

        })
    }

    private fun dataAvailable() {
        textNoStarred.visibility = View.GONE
    }

    private fun noDataAvailable() {
        textNoStarred.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        starredProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        starredProgressBar.visibility = View.VISIBLE
    }
}