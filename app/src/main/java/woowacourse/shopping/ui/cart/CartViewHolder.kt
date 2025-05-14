package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.domain.product.Product

class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.product = item
    }
}
