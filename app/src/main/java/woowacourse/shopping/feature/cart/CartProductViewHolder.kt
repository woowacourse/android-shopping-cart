package woowacourse.shopping.feature.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding

class CartProductViewHolder(
    private val binding: ItemCartProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: CartProductItemModel) {
        binding.itemModel = cartProduct
        binding.position = bindingAdapterPosition
    }
}
