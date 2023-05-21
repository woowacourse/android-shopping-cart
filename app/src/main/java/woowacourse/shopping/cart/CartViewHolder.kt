package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartViewHolder(
    private val binding: ItemCartProductListBinding,
    onCartItemRemoveButtonViewClick: (Int) -> Unit,
    onCheckBoxViewClick: (Int) -> Unit,
    onMinusAmountButtonClick: (CartProductModel) -> Unit,
    onPlusAmountButtonClick: (CartProductModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartProductListRemoveButton.setOnClickListener { onCartItemRemoveButtonViewClick(bindingAdapterPosition) }

        binding.cartProductCheckbox.setOnClickListener { onCheckBoxViewClick(bindingAdapterPosition) }

        binding.cartProductAmountMinusButton.setOnClickListener { onMinusAmountButtonClick(binding.cartProduct!!) }
        binding.cartProductAmountPlusButton.setOnClickListener { onPlusAmountButtonClick(binding.cartProduct!!) }
    }

    fun bind(cartProduct: CartProductModel) {
        binding.cartProduct = cartProduct
    }
}
