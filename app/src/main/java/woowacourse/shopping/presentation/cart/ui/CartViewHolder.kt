package woowacourse.shopping.presentation.cart.ui

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.presentation.cart.CartViewModel

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val onClickHandler: OnClickHandler
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: CartProduct) {
        binding.product = product
        binding.executePendingBindings()

        binding.ivCartProductRemove.setOnClickListener {
            onClickHandler.onRemoveCartProductClick(product.product.id)
        }

        binding.tvCartPlus.setOnClickListener {
            onClickHandler.onIncreaseCount(product)
        }

        binding.tvCartMinus.setOnClickListener {
            onClickHandler.onDecreaseCount(product)
        }
    }

    interface OnClickHandler {
        fun onRemoveCartProductClick(id: Int)
        fun onIncreaseCount(product: CartProduct)
        fun onDecreaseCount(product: CartProduct)
    }
}