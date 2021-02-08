package gap.com.githubrepos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import gap.com.githubrepos.R
import gap.com.githubrepos.adapter.ReposAdapter
import gap.com.githubrepos.utils.CACHE_SIZE
import gap.com.githubrepos.viewmodel.FollowerViewModel
import gap.com.githubrepos.viewmodel.FollowingViewModel
import gap.com.githubrepos.viewmodel.RepositoriesViewModel
import kotlinx.android.synthetic.main.fragment_repos.*


class ReposFragment : BaseFragment() {

    private lateinit var reposAdapter: ReposAdapter
    private val viewModel: RepositoriesViewModel by viewModels()
    private val viewModelFollowers: FollowerViewModel by viewModels()
    private val viewModelFollowing: FollowingViewModel by viewModels()

    override val getFragmentLayout: Int
        get() = R.layout.fragment_repos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reposAdapter.setOnItemClickListener {
            toast(it.name)
        }
    }

    fun setupRecyclerView(){
        reposAdapter = ReposAdapter()
        rv_repos.apply {
            setRecyclerviewDivider(context, this, R.drawable.divider_list)
            adapter = reposAdapter
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(CACHE_SIZE)
        }
    }
}