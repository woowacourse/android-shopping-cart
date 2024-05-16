package woowacourse.shopping.feature.cart.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class CartViewHolder(private val binding: ItemCartBinding) : ViewHolder(binding.root) {
    fun bind(
        onClickExit: OnClickExit,
        cartItem: CartItem,
    ) {
        binding.cartItem = cartItem
        binding.ivCartExit.setOnClickListener {
            onClickExit(cartItem.product)
        }
    }
}

typealias OnClickExit = (product: Product) -> Unit
