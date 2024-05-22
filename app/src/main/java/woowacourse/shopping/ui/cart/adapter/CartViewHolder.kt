package woowacourse.shopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.ui.utils.AddCartQuantityBundle
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class CartViewHolder(private val binding: ItemCartBinding) : ViewHolder(binding.root) {
    fun bind(
        cartItem: CartItem,
        onClickExit: OnClickExit,
        onIncreaseProductQuantity: OnIncreaseProductQuantity,
        onDecreaseProductQuantity: OnDecreaseProductQuantity,
    ) {
        binding.cartItem = cartItem
        binding.ivCartExit.setOnClickListener {
            onClickExit(cartItem)
        }
        binding.addCartQuantityBundle =
            AddCartQuantityBundle(
                cartItem.product,
                onIncreaseProductQuantity,
                onDecreaseProductQuantity,
            )
    }
}

typealias OnClickExit = (cartItem: CartItem) -> Unit
