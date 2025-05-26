package woowacourse.shopping.view.product.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewProductBinding
import woowacourse.shopping.domain.Product

class ViewHolder(
    val binding: ItemRecentlyViewProductBinding,
    val navigateToProductDetail: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.navigateToProductDetail = navigateToProductDetail
    }

    fun bind(product: Product) {
        binding.recentlyViewedProduct = product
    }
}
