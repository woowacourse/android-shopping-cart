package woowacourse.shopping.feature.main.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class RecentAdapter : ListAdapter<RecentProductItemModel, RecentViewHolder>(RecentDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        return RecentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setItems(newItems: List<RecentProductItemModel>) {
        submitList(newItems)
    }

    companion object {
        private val RecentDiffCallBack = object : DiffUtil.ItemCallback<RecentProductItemModel>() {
            override fun areItemsTheSame(
                oldItem: RecentProductItemModel,
                newItem: RecentProductItemModel
            ): Boolean {
                return oldItem.recentProduct.productUiModel.id == newItem.recentProduct.productUiModel.id
            }

            override fun areContentsTheSame(
                oldItem: RecentProductItemModel,
                newItem: RecentProductItemModel
            ): Boolean {
                return oldItem.recentProduct == newItem.recentProduct
            }

        }
    }
}
