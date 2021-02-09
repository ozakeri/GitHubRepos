package gap.com.githubrepos.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gap.com.githubrepos.R
import gap.com.githubrepos.adapter.SearchAdapter
import gap.com.githubrepos.utils.CACHE_SIZE
import gap.com.githubrepos.utils.MAX_PAGE
import gap.com.githubrepos.utils.MIN_PAGE
import gap.com.githubrepos.utils.Resource
import gap.com.githubrepos.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_starred.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override val getFragmentLayout: Int
        get() = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getUsersList()
        observeSearchRepository()
    }

    fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        rv_search.apply {
            setRecyclerviewDivider(context, this, R.drawable.divider_list)
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(CACHE_SIZE)
        }
    }

    private fun getUsersList() {
        var job: Job? = null
        editTextSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                showProgressBar(searchProgressBar)
                delay(500)
                editable.let {
                    if (editable.toString().isNotEmpty()){
                        searchViewModel.search(editable.toString(), MIN_PAGE, MAX_PAGE)
                    }else{
                        hideProgressBar(searchProgressBar)
                    }
                }
            }
        }
    }

    private fun observeSearchRepository() {

        searchViewModel.searchResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar(searchProgressBar)
                    searchAdapter.differ.submitList(response.value.items)
                }

                is Resource.Failure -> {
                    hideProgressBar(searchProgressBar)
                    if (response.isNetworkError) {
                        toast("Check your connection!")
                    }
                }
            }

        })

    }

}