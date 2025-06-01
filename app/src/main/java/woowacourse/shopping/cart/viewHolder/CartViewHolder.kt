package woowacourse.shopping.cart.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.cart.event.CartEventHandler
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewHolder(
    private val binding: CartItemBinding,
    private val cartHandler: CartEventHandler,
    private val handler: ProductQuantityHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: ProductUiModel) {
        binding.product = cartProduct
        binding.cartHandler = cartHandler
        binding.handler = handler
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            cartHandler: CartEventHandler,
            handler: ProductQuantityHandler,
        ): CartViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CartItemBinding.inflate(inflater, parent, false)
            binding.cartHandler = cartHandler
            binding.handler = handler
            return CartViewHolder(binding, cartHandler, handler)
        }
    }
}
