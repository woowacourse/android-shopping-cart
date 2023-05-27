package woowacourse.shopping.feature.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductUiModel

class CartProductViewHolder(
    private val binding: ItemCartProductBinding,
    private val cartProductClickListener: CartProductClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: CartProductUiModel) {
        binding.apply {
            this.cartProduct = cartProduct
            checkbox.isChecked = cartProduct.isSelected
            checkbox.setOnClickListener {
                cartProductClickListener.onCheckClick(cartProduct, checkbox.isChecked)
            }
            deleteBtn.setOnClickListener {
                cartProductClickListener.onDeleteClick(cartProduct)
            }

            countView.count = cartProduct.count
            countView.plusClickListener = {
                cartProductClickListener.onPlusClick(cartProduct, countView.count)
            }
            countView.minusClickListener = {
                cartProductClickListener.onMinusClick(cartProduct, countView.count)
            }
        }
    }
}
