package woowacourse.shopping.ui.shopping.recentproduct

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.model.UiRecentProduct

class RecentProductAdapter(private val onItemClick: (UiProduct) -> Unit) :
    ListAdapter<UiRecentProduct, RecentProductViewHolder>(recentProductDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder =
        RecentProductViewHolder(parent) { onItemClick(currentList[it].product) }

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val recentProductDiffUtil = object : DiffUtil.ItemCallback<UiRecentProduct>() {
            override fun areItemsTheSame(oldItem: UiRecentProduct, newItem: UiRecentProduct):
                Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiRecentProduct, newItem: UiRecentProduct):
                Boolean = oldItem == newItem
        }
    }
}
