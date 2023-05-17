package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartOrdinalProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartViewHolder(
    private val binding: ItemCartProductListBinding,
    onCartItemRemoveButtonViewClick: (CartOrdinalProductModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartProductListRemoveButton.setOnClickListener {
            onCartItemRemoveButtonViewClick(
                binding.cartOrdinalProductModel ?: return@setOnClickListener
            )
        }
    }

    fun bind(cartOrdinalProduct: CartOrdinalProductModel) {
        binding.cartOrdinalProductModel = cartOrdinalProduct
    }
}
