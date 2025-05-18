package woowacourse.shopping.view.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductViewHolder(
    val binding: ItemProductBinding,
    val onSelectedProduct: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onSelectedProduct = onSelectedProduct
    }

    fun bind(product: Product) {
        binding.product = product
    }
}
