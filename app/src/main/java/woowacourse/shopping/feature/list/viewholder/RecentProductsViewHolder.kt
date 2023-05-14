package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.feature.list.adapter.RecentProductsAdapter
import woowacourse.shopping.feature.list.item.ProductView

class RecentProductsViewHolder(binding: ViewBinding) : ItemViewHolder(binding) {
    private val binding = binding as ItemRecentProductListBinding

    override fun bind(productView: ProductView, onClick: (ProductView) -> Unit) {
        binding.recentListRv.adapter = RecentProductsAdapter(productView as ProductView.RecentProductsItem)
    }
}
