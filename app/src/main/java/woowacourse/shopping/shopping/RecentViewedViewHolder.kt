package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.RecentViewedLayoutBinding

class RecentViewedViewHolder private constructor(
    binding: RecentViewedLayoutBinding
) : ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.RecentViewedProducts, RecentViewedLayoutBinding>(binding) {

    override fun bind(itemData: ShoppingRecyclerItem.RecentViewedProducts) {
        binding.recyclerViewRecentViewedProduct.adapter = RecentViewedRecyclerAdapter(
            products = itemData.values
        )
    }

    companion object {
        fun from(parent: ViewGroup): RecentViewedViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecentViewedLayoutBinding.inflate(layoutInflater, parent, false)

            return RecentViewedViewHolder(binding)
        }
    }
}
