package gap.com.githubrepos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.android.githubservice.entity.follower_following.FollowerFollowingResponse
import com.faramarzaf.sdk.af_android_sdk.core.helper.GlideHelper
import gap.com.githubrepos.R
import kotlinx.android.synthetic.main.item_list_followers_following.view.*

class FollowersFollowingAdapter :
    RecyclerView.Adapter<FollowersFollowingAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback =
        object : DiffUtil.ItemCallback<FollowerFollowingResponse.FollowerFollowingItem>() {
            override fun areItemsTheSame(
                oldItem: FollowerFollowingResponse.FollowerFollowingItem,
                newItem: FollowerFollowingResponse.FollowerFollowingItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowerFollowingResponse.FollowerFollowingItem,
                newItem: FollowerFollowingResponse.FollowerFollowingItem
            ): Boolean {
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_followers_following, parent, false)
        )
    }

    private var onItemClickListener: ((FollowerFollowingResponse.FollowerFollowingItem) -> Unit)? = null

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val infoData = differ.currentList[position]
        holder.itemView.apply {
            GlideHelper.circularImage(context, infoData.avatarUrl.toString(), imageFollowersFollowing)
            textFollowersFollowing.text = infoData.login
            setOnClickListener {
                onItemClickListener?.let { it(infoData) }
            }
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (FollowerFollowingResponse.FollowerFollowingItem) -> Unit) {
        onItemClickListener = listener
    }


}