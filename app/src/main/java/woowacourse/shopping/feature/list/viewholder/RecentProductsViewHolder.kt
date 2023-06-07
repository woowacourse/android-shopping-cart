package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.feature.list.adapter.RecentProductsAdapter
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.list.item.ProductView.RecentProductsItem

class RecentProductsViewHolder(
    parent: ViewGroup,
) : ItemViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_recent_product_list, parent, false),
) {
    private val binding = ItemRecentProductListBinding.bind(itemView)

    override fun bind(productView: ProductView) {
        binding.recentListRv.adapter = RecentProductsAdapter(productView as RecentProductsItem)
    }
}
