package woowacourse.shopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.CartListener
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel
import woowacourse.shopping.ui.utils.AddCartQuantityBundle

class CartViewHolder(private val binding: ItemCartBinding) : ViewHolder(binding.root) {
    fun bind(
        productUiModel: ProductUiModel,
        cartListener: CartListener,
    ) {
        binding.productUiModel = productUiModel
        binding.ivCartExit.setOnClickListener {
            cartListener.deleteCartItem(productUiModel.productId)
        }
        binding.addCartQuantityBundle =
            AddCartQuantityBundle(
                productUiModel.productId,
                productUiModel.quantity,
                cartListener::increaseQuantity,
                cartListener::decreaseQuantity,
            )
    }
}
