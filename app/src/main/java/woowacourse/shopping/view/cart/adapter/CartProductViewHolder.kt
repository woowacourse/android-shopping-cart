package woowacourse.shopping.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.CartProduct

class CartProductViewHolder(
    private val binding: ItemCartProductBinding,
    eventHandler: EventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = eventHandler
    }

    fun bind(product: CartProduct) {
        binding.cartProduct = product
    }

    interface EventHandler {
        fun onProductRemoveClick(item: CartProduct)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: EventHandler,
        ): CartProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCartProductBinding.inflate(inflater, parent, false)
            return CartProductViewHolder(binding, eventListener)
        }
    }
}
