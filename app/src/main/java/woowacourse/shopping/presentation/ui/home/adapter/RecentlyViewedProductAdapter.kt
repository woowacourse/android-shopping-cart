package woowacourse.shopping.presentation.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.RecentlyViewedItemViewHolder

class RecentlyViewedProductAdapter(private val productClickListener: ProductClickListener) :
    ListAdapter<RecentlyViewedProduct, RecentlyViewedItemViewHolder>(RecentlyViewedProductComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedItemViewHolder {
        return RecentlyViewedItemViewHolder(
            RecentlyViewedItemViewHolder.getView(parent),
            productClickListener,
        )
    }

    override fun onBindViewHolder(holder: RecentlyViewedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RecentlyViewedProductComparator : DiffUtil.ItemCallback<RecentlyViewedProduct>() {
        override fun areItemsTheSame(
            oldItem: RecentlyViewedProduct,
            newItem: RecentlyViewedProduct,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecentlyViewedProduct,
            newItem: RecentlyViewedProduct,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
