package woowacourse.shopping.view.products.recentproduct

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.products.ProductListActionHandler

class RecentProductViewHolder(val binding: ItemRecentProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        productListActionHandler: ProductListActionHandler,
    ) {
        binding.product = product
        binding.productListActionHandler = productListActionHandler
    }
}
