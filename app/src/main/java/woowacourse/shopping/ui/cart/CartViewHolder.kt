package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductListBinding
import woowacourse.shopping.ui.model.CartProductModel

class CartViewHolder(
    private val binding: ItemCartProductListBinding,
    onCartItemRemoveButtonViewClick: (Int) -> Unit,
    onCheckBoxViewClick: (Int) -> Unit,
    onMinusAmountButtonViewClick: (Int) -> Unit,
    onPlusAmountButtonViewClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartProductListRemoveButton.setOnClickListener { onCartItemRemoveButtonViewClick(bindingAdapterPosition) }

        binding.cartProductCheckbox.setOnClickListener { onCheckBoxViewClick(bindingAdapterPosition) }

        binding.cartProductCounter.productAmountMinusButton.setOnClickListener { onMinusAmountButtonViewClick(bindingAdapterPosition) }
        binding.cartProductCounter.productAmountPlusButton.setOnClickListener { onPlusAmountButtonViewClick(bindingAdapterPosition) }
    }

    fun bind(cartProduct: CartProductModel) {
        binding.cartProduct = cartProduct
    }
}
