package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.ProductCountClickListener
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.model.CartProductUiModel

class ShoppingCartItemViewHolder(
    private val binding: ItemShoppingCartBinding,
    private val onCheckBoxClicked: (id: Int, isSelected: Boolean) -> Unit,
    private val countClickListener: ProductCountClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        cartProduct: CartProductUiModel,
        onRemoveClicked: (Int) -> Unit,
    ) {
        with(binding) {
            this.cartProduct = cartProduct
            checkboxSelected.isChecked = cartProduct.isSelected
            imageRemoveProduct.setOnClickListener { onRemoveClicked(adapterPosition) }
            countView.count = cartProduct.count
            countView.plusClickListener = {
                cartProduct.count += CALCULATE_AMOUNT
                countClickListener.onPlusClick(cartProduct.product.id)
            }
            countView.minusClickListener = {
                cartProduct.count -= CALCULATE_AMOUNT
                countClickListener.onMinusClick(cartProduct.product.id)
            }
            checkboxSelected.setOnClickListener {
                cartProduct.isSelected = checkboxSelected.isChecked
                onCheckBoxClicked(cartProduct.product.id, checkboxSelected.isChecked)
            }
        }
    }

    companion object {
        private const val CALCULATE_AMOUNT = 1

        fun from(
            parent: ViewGroup,
            onCheckBoxClicked: (Int, Boolean) -> Unit,
            countClickListener: ProductCountClickListener,
        ): ShoppingCartItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartBinding.inflate(layoutInflater, parent, false)

            return ShoppingCartItemViewHolder(binding, onCheckBoxClicked, countClickListener)
        }
    }
}
