package gap.com.githubrepos.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faramarzaf.sdk.af_android_sdk.core.interfaces.CallbackSnackBar
import com.faramarzaf.sdk.af_android_sdk.core.interfaces.DialogCallback
import com.faramarzaf.sdk.af_android_sdk.core.ui.SimpleSnackbar
import com.faramarzaf.sdk.af_android_sdk.core.ui.dialog.PublicDialog
import dagger.hilt.android.AndroidEntryPoint
import gap.com.githubrepos.R
import gap.com.githubrepos.adapter.FavoriteAdapter
import gap.com.githubrepos.entitiy.Item
import gap.com.githubrepos.utils.CACHE_SIZE
import gap.com.githubrepos.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class FavoriteFragment : BaseFragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private val searchViewModel: SearchViewModel by viewModels()

    override val getFragmentLayout: Int
        get() = R.layout.fragment_favorite

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        swipeRemoving(view)
        faveObserve()
        favoriteAdapter.setOnItemClickListener {
            toast(it.login)
        }

        imgDeleteAll.setOnClickListener {
            deleteAllFavorite()
        }
    }

    fun faveObserve() {
        searchViewModel.getAllUser().observe(viewLifecycleOwner, Observer { response ->
            favoriteAdapter.diff.submitList(response)
            println("response=====" + response.size)
            for (i in response) {
                println(i.login.toString())
            }
            if (response.isEmpty()) {
                imgDeleteAll.isEnabled = false
                noDataAvailable()
            } else {
                imgDeleteAll.isEnabled = true
                dataAvailable()
            }
        })
    }

    private fun swipeRemoving(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val user = favoriteAdapter.diff.currentList[position]
                searchViewModel.deleteUser(user)
                SimpleSnackbar.show(view, "Successfully deleted user", "Undo", Color.GRAY,
                    Color.WHITE, Color.BLUE, true, object : CallbackSnackBar {
                        override fun onActionClick() {
                            searchViewModel.insertUser(user)
                        }
                    })
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_saved)
        }
    }

    private fun deleteAllFavorite() {
        PublicDialog.yesNoDialog(
            requireContext(),
            getString(R.string.remove_all_title),
            getString(R.string.msg_dialog_remove_all),
            getString(R.string.yes),
            getString(R.string.no),
            R.drawable.ic_delete,
            object : DialogCallback {
                override fun onNegativeButtonClicked() {
                    return
                }

                override fun onPositiveButtonClicked() {
                    searchViewModel.deleteAll()
                }

            }
        )
    }

    fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        rv_saved.apply {
            setRecyclerviewDivider(context, this, R.drawable.divider_list)
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(CACHE_SIZE)
        }
    }

    private fun noDataAvailable() {
        textNoFavUser.visibility = View.VISIBLE
    }

    private fun dataAvailable() {
        textNoFavUser.visibility = View.GONE
    }
}