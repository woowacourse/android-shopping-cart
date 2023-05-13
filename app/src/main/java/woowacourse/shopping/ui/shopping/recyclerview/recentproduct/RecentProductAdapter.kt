package woowacourse.shopping.ui.shopping.recyclerview.recentproduct

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.UiRecentProduct

class RecentProductAdapter(private val onItemClick: (UiRecentProduct) -> Unit) :
    ListAdapter<UiRecentProduct, RecentProductViewHolder>(recentProductDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder =
        RecentProductViewHolder(parent) { pos -> onItemClick(currentList[pos]) }

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
