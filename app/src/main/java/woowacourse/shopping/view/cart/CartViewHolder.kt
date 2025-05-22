package woowacourse.shopping.view.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.model.products.Product

class CartViewHolder(
    private val binding: ItemProductInCartBinding,
    private val onProductRemoveClickListener: OnProductRemoveClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.onProductRemoveClickListener = onProductRemoveClickListener
        binding.product = item
    }
}
