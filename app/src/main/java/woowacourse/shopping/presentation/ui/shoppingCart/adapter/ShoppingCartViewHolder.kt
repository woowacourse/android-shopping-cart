package woowacourse.shopping.presentation.ui.shoppingCart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartSetClickListener

class ShoppingCartViewHolder(
    onClick: ShoppingCartSetClickListener,
    private val binding: ItemShoppingCartProductBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setClickListener = onClick
    }

    fun bind(product: ProductInCartUiState) {
        binding.shoppingCart = product
    }

    companion object {
        fun getView(parent: ViewGroup): ItemShoppingCartProductBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemShoppingCartProductBinding.inflate(layoutInflater, parent, false)
        }
    }
}
