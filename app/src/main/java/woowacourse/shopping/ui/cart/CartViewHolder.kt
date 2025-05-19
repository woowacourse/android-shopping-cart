package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.ui.custom.CartCountView

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val onClickHandler: OnClickHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClickHandler = onClickHandler
    }

    fun bind(cartProduct: CartProduct) {
        binding.cartProduct = cartProduct
        binding.cartProductCount.setCount(cartProduct.quantity)
        binding.cartProductCount.setOnClickHandler(
            object : CartCountView.OnClickHandler {
                override fun onIncreaseClick() {
                    onClickHandler.onIncreaseClick(cartProduct.product.id)
                }

                override fun onDecreaseClick() {
                    onClickHandler.onDecreaseClick(cartProduct.product.id)
                }
            },
        )
    }

    interface OnClickHandler {
        fun onRemoveCartProductClick(id: Int)

        fun onIncreaseClick(id: Int)

        fun onDecreaseClick(id: Int)
    }
}
