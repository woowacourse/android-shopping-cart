package woowacourse.shopping.feature.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductUiModel

class CartProductViewHolder(
    private val binding: ItemCartProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: CartProductUiModel, onClick: (CartProductUiModel) -> Unit) {
        binding.cartProduct = cartProduct
        binding.deleteBtn.setOnClickListener { onClick.invoke(cartProduct) }
    }
}
