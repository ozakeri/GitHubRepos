package gap.com.githubrepos.entitiy

import com.google.gson.annotations.SerializedName

data class SearchResponse(    @SerializedName("incomplete_results")
                              val incompleteResults: Boolean,
                              @SerializedName("items")
                              val items: MutableList<Item>,
                              @SerializedName("total_count")
                              val totalCount: Int)