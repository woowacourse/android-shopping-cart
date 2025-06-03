package woowacourse.shopping.presentation.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.view.ItemCounterListener

class CartViewHolder(
    private val binding: ItemCartBinding,
    eventListener: CartAdapter.CartEventListener,
    itemCounterListener: ItemCounterListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.eventListener = eventListener
        binding.cartItemCounter.listener = itemCounterListener
    }

    fun bind(cartItem: CartItem) {
        binding.cartItem = cartItem
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: CartAdapter.CartEventListener,
            itemCounterListener: ItemCounterListener,
        ): CartViewHolder {
            val binding =
                ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, eventListener, itemCounterListener)
        }
    }
}
