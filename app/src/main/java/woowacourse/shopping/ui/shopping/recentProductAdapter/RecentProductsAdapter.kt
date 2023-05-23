package woowacourse.shopping.ui.shopping.recentProductAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.ui.shopping.recentProductAdapter.viewHolder.RecentProductViewHolder

class RecentProductsAdapter(
    private val onClickListener: RecentProductsListener
) : ListAdapter<RecentProductItem, RecentProductViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        return RecentProductViewHolder.from(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private class DiffCallback : DiffUtil.ItemCallback<RecentProductItem>() {
            override fun areItemsTheSame(oldItem: RecentProductItem, newItem: RecentProductItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: RecentProductItem, newItem: RecentProductItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
