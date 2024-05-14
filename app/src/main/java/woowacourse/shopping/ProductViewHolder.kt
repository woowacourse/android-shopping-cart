package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemProductBinding

class ProductViewHolder(private val binding: ItemProductBinding) : ViewHolder(binding.root) {
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
