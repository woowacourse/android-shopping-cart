package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.model.CartProductUiModel

class ShoppingCartItemViewHolder(
    private val binding: ItemShoppingCartBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        cartProductUiModel: CartProductUiModel,
        onRemoveClicked: (Int) -> Unit,
    ) {
        with(binding) {
            cartProduct = cartProductUiModel
            imageRemoveProduct.setOnClickListener { onRemoveClicked(adapterPosition) }
        }
    }

    companion object {
        fun from(parent: ViewGroup): ShoppingCartItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartBinding.inflate(layoutInflater, parent, false)

            return ShoppingCartItemViewHolder(binding)
        }
    }
}
