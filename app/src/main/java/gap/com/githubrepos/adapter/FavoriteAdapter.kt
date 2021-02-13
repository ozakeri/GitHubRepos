package gap.com.githubrepos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.faramarzaf.sdk.af_android_sdk.core.helper.GlideHelper
import gap.com.githubrepos.R
import gap.com.githubrepos.entitiy.Item
import kotlinx.android.synthetic.main.item_list_saved.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    class FavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_saved, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    private var onItemClickListener: ((Item) -> Unit)? = null

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {

        val favItem = diff.currentList[position]

        holder.itemView.apply {
            text_save_name.text = favItem.login.toString()
            GlideHelper.circularImage(context, favItem.avatarUrl.toString(), imageSavedUsers)

            setOnClickListener {
                onItemClickListener?.let { it(favItem) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

}