package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.model.CartProductUiModel

class ShoppingCartItemViewHolder(
    private val binding: ItemShoppingCartBinding,
    private val onCheckBoxClicked: (id: Int, isSelected: Boolean) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        cartProductUiModel: CartProductUiModel,
        onRemoveClicked: (Int) -> Unit,
        products: List<CartProductUiModel>,
    ) {
        with(binding) {
            cartProduct = cartProductUiModel
            checkboxSelected.isChecked = cartProductUiModel.isSelected
            imageRemoveProduct.setOnClickListener { onRemoveClicked(adapterPosition) }
            checkboxSelected.setOnClickListener {
                products[adapterPosition].isSelected = checkboxSelected.isChecked
                onCheckBoxClicked(cartProductUiModel.product.id, checkboxSelected.isChecked)
            }
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onCheckBoxClicked: (Int, Boolean) -> Unit,
        ): ShoppingCartItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartBinding.inflate(layoutInflater, parent, false)

            return ShoppingCartItemViewHolder(binding, onCheckBoxClicked)
        }
    }
}
