package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductsViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        onClickProductItem: OnClickProductItem,
        product: Product,
    ) {
        binding.product = product
        binding.root.setOnClickListener {
            onClickProductItem(product.id)
        }
    }
}

typealias OnClickProductItem = (productId: Long) -> Unit
