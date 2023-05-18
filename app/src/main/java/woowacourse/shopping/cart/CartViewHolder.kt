package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartViewHolder(
    private val binding: ItemCartProductListBinding,
    onCartItemRemoveButtonViewClick: (CartProductModel) -> Unit,
    onMinusClick: (CartProductModel) -> Unit,
    onPlusClick: (CartProductModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartProductListRemoveButton.setOnClickListener {
            onCartItemRemoveButtonViewClick(
                binding.cartProduct ?: return@setOnClickListener
            )
        }
        binding.onMinusClick = onMinusClick
        binding.onPlusClick = onPlusClick
    }

    fun bind(cartOrdinal: CartProductModel) {
        binding.cartProduct = cartOrdinal
    }
}
