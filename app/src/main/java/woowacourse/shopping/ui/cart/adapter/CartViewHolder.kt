package woowacourse.shopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.CartListener
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel
import woowacourse.shopping.ui.utils.AddQuantityListener

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val cartListener: CartListener,
) : ViewHolder(binding.root) {
    fun bind(
        productUiModel: ProductUiModel,
    ) {
        binding.product = productUiModel
        binding.listener = cartListener
        binding.quantityListener = object : AddQuantityListener {
            override fun onIncreaseProductQuantity() {
                cartListener.onClickIncreaseQuantity(productUiModel.productId)
            }

            override fun onDecreaseProductQuantity() {
                cartListener.onClickDecreaseQuantity(productUiModel.productId)
            }

        }
    }
}
