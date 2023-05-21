package woowacourse.shopping.feature.main.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.RecentProductUiModel

class RecentAdapter(private val recentProductClickListener: RecentProductClickListener) :
    ListAdapter<RecentProductUiModel, RecentViewHolder>(RecentDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        return RecentViewHolder.create(parent, recentProductClickListener)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setItems(newItems: List<RecentProductUiModel>) {
        submitList(newItems)
    }

    companion object {
        private val RecentDiffCallBack = object : DiffUtil.ItemCallback<RecentProductUiModel>() {
            override fun areItemsTheSame(
                oldItem: RecentProductUiModel,
                newItem: RecentProductUiModel
            ): Boolean {
                return oldItem.productUiModel.id == newItem.productUiModel.id
            }

            override fun areContentsTheSame(
                oldItem: RecentProductUiModel,
                newItem: RecentProductUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
