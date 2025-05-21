package woowacourse.shopping.presentation.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.model.CartItemUiModel

class CartViewHolder(
    private val binding: ItemCartBinding,
    eventListener: CartAdapter.CartEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.eventListener = eventListener
        binding.viewQuantitySelector.listener = eventListener
    }

    fun bind(cartItem: CartItemUiModel) {
        binding.cartItem = cartItem
        binding.viewQuantitySelector.quantity = cartItem.quantity
        binding.viewQuantitySelector.productId = cartItem.product.id
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: CartAdapter.CartEventListener,
        ): CartViewHolder {
            val binding =
                ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, eventListener)
        }
    }
}
