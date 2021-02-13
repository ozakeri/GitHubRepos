package gap.com.githubrepos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.faramarzaf.sdk.af_android_sdk.core.helper.GlideHelper
import gap.com.githubrepos.R
import gap.com.githubrepos.entitiy.Item
import kotlinx.android.synthetic.main.item_list_searched_users.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    private var onItemClickListener: ((Item) -> Unit)? = null
    private var onSaveUserClickListener: ((Item, ImageView) -> Unit)? = null
    private var getItemInstance: ((Item) -> Unit)? = null
    private var getViewFromAdapter: ((ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_searched_users, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchInfo = differ.currentList[position]
        val imageFav = holder.itemView.imageFav

        getItemInstance?.let {
            it(searchInfo)
        }

        imageFav.setOnClickListener {
            onSaveUserClickListener?.let { item ->
                item(searchInfo, imageFav)
            }
        }

        holder.itemView.apply {
            GlideHelper.circularImage(context, searchInfo.avatarUrl.toString(), avatarUser)
            textUser.text = searchInfo.login

            getViewFromAdapter?.let { views ->
                views(imageFav)
            }
            setOnClickListener {
                onItemClickListener?.let { it(searchInfo) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnSaveUserClickListener(listener: (Item, ImageView) -> Unit) {
        onSaveUserClickListener = listener
    }

    fun getItemInstance(listener: (Item) -> Unit) {
        getItemInstance = listener
    }

    fun getViewFromAdapter(listener: (ImageView) -> Unit) {
        getViewFromAdapter = listener
    }
}