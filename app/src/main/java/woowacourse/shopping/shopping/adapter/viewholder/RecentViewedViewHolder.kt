package woowacourse.shopping.shopping.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.RecentViewedLayoutBinding
import woowacourse.shopping.shopping.adapter.RecentViewedRecyclerAdapter

class RecentViewedViewHolder private constructor(
    binding: RecentViewedLayoutBinding,
    private val onProductClicked: (productId: Int) -> Unit,
) : ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.RecentViewedProducts, RecentViewedLayoutBinding>(
    binding
) {

    override fun bind(itemData: ShoppingRecyclerItem.RecentViewedProducts) {
        binding.recyclerViewRecentViewedProduct.adapter = RecentViewedRecyclerAdapter(
            products = itemData.values,
            onProductClicked = onProductClicked
        )
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onRecentViewedProductProductClicked: (id: Int) -> Unit,
        ): RecentViewedViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecentViewedLayoutBinding.inflate(layoutInflater, parent, false)

            return RecentViewedViewHolder(
                binding = binding,
                onProductClicked = onRecentViewedProductProductClicked
            )
        }
    }
}
