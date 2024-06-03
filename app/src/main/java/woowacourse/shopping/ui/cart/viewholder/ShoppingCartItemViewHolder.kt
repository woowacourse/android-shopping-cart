package woowacourse.shopping.ui.cart.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.cart.CartProductListener

class ShoppingCartItemViewHolder(
    private val binding: HolderCartBinding,
    private val onCartProductListener: CartProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.cartProductListener = onCartProductListener
    }
}
