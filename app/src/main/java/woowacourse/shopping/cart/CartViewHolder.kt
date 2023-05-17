package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartViewHolder(
    private val binding: ItemCartProductListBinding,
    onCartItemRemoveButtonViewClick: (CartProductModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartProductListRemoveButton.setOnClickListener {
            onCartItemRemoveButtonViewClick(
                binding.cartProduct ?: return@setOnClickListener
            )
        }
    }

    fun bind(cartOrdinal: CartProductModel) {
        binding.cartProduct = cartOrdinal
    }
}
