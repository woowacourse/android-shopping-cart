package woowacourse.shopping.view.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.view.core.handler.CartQuantityHandler
import woowacourse.shopping.view.main.vm.state.ProductState

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val handler: Handler,
    private val cartQuantityHandler: CartQuantityHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProductState) {
        with(binding) {
            model = item
            eventHandler = handler
            cartQuantityEventHandler = cartQuantityHandler
        }
    }

    interface Handler {
        fun onClickDeleteItem(cartId: Long)
    }
}
