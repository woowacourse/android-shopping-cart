package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Product

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val onRemoveButtonClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.ivRemove.setOnClickListener {
            onRemoveButtonClick(product.id)
        }
    }
}
