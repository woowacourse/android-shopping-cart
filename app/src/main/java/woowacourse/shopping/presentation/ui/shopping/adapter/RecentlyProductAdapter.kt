package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemRecentlyProductBinding
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler
import woowacourse.shopping.presentation.ui.shopping.viewholder.RecentlyProductViewHolder

class RecentlyProductAdapter(
    private val actionHandler: ShoppingActionHandler,
) : ListAdapter<RecentlyViewedProduct, RecentlyProductViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyProductViewHolder {
        val binding = ItemRecentlyProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentlyProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecentlyProductViewHolder,
        position: Int,
    ) {
        val recentlyViewedProduct = getItem(position)
        holder.bind(recentlyViewedProduct, actionHandler)
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<RecentlyViewedProduct>() {
                override fun areItemsTheSame(
                    oldItem: RecentlyViewedProduct,
                    newItem: RecentlyViewedProduct,
                ): Boolean {
                    return oldItem.productId == newItem.productId
                }

                override fun areContentsTheSame(
                    oldItem: RecentlyViewedProduct,
                    newItem: RecentlyViewedProduct,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
