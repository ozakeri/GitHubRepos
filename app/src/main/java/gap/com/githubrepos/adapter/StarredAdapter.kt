package gap.com.githubrepos.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.android.githubservice.entity.starred.StarredResponse
import gap.com.githubrepos.R
import gap.com.githubrepos.utils.LanguageColorGenerator
import gap.com.githubrepos.utils.thousandPrinter
import kotlinx.android.synthetic.main.item_list_repos.view.*

class StarredAdapter : RecyclerView.Adapter<StarredAdapter.StareViewHolder>() {

    class StareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback =
        object : DiffUtil.ItemCallback<StarredResponse.StarredModelItem>() {
            override fun areItemsTheSame(
                oldItem: StarredResponse.StarredModelItem,
                newItem: StarredResponse.StarredModelItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StarredResponse.StarredModelItem,
                newItem: StarredResponse.StarredModelItem
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StareViewHolder {
        return StareViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_repos, parent, false)
        )
    }

    private var onItemClickListener: ((StarredResponse.StarredModelItem) -> Unit)? = null

    override fun onBindViewHolder(holder: StareViewHolder, position: Int) {

        val repoInfo = differ.currentList[position]
        holder.itemView.apply {
            text_repo_name.text = repoInfo.name
            text_repo_desc.text = repoInfo.description
            if (repoInfo.stargazersCount!! > 1000 || repoInfo.forksCount!! > 1000) {
                val startValue = thousandPrinter(repoInfo.stargazersCount.toString())
                val forkValue = thousandPrinter(repoInfo.forksCount.toString())
                text_stars.text = startValue
                text_forks.text = forkValue
            } else {
                text_stars.text = "☆ " + repoInfo.stargazersCount.toString()
                text_forks.text = repoInfo.forksCount.toString()
            }

            val keyColor = repoInfo.language.toString()
            val codeColor = LanguageColorGenerator.getColors(context, keyColor)
            if (codeColor != null)
                ViewCompat.setBackgroundTintList(
                    view_colored_language,
                    ColorStateList.valueOf(Color.parseColor(codeColor))
                )

            val language = repoInfo.language
            text_language.text = language
            if (language.isNullOrEmpty()) {
                text_language.text = "-"
                view_colored_language.visibility = View.GONE
            }

            setOnClickListener {
                onItemClickListener?.let { it(repoInfo) }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun setOnItemClickListener(listener: (StarredResponse.StarredModelItem) -> Unit) {
        onItemClickListener = listener
    }
}