package woowacourse.shopping.view.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.Product

class CartProductViewHolder(
    val binding: ItemCartProductBinding,
    val onDeleteClick: (Product, Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.toDelete = onDeleteClick
    }

    fun bind(
        product: Product,
        position: Int,
    ) {
        binding.product = product
        binding.position = position
    }
}
