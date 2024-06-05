package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler

class RecentAdapter(
    private val shoppingActionHandler: ShoppingActionHandler,
) : ListAdapter<RecentProduct, RecentViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentBinding.inflate(inflater, parent, false)
        return RecentViewHolder(binding, shoppingActionHandler)
    }

    override fun onBindViewHolder(
        holder: RecentViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<RecentProduct>() {
                override fun areItemsTheSame(
                    oldItem: RecentProduct,
                    newItem: RecentProduct,
                ): Boolean {
                    return oldItem.productId == newItem.productId
                }

                override fun areContentsTheSame(
                    oldItem: RecentProduct,
                    newItem: RecentProduct,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
