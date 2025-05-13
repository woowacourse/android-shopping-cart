package woowacourse.shopping.ui.productlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.PriceFormatter

class ProductListViewHolder(
    private val binding: ProductItemBinding,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productClickListener = productClickListener
    }

    fun bind(item: Product) {
        binding.product = item
        binding.priceFormatter = PriceFormatter
    }
}
