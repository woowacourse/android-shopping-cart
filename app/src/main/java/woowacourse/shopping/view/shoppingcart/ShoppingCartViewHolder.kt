package woowacourse.shopping.view.shoppingcart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.ShoppingCartItem

class ShoppingCartViewHolder(
    private val binding: ItemShoppingCartProductBinding,
    onProductRemove: (ShoppingCartItem, Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onProductRemove = onProductRemove
    }

    fun bind(
        item: ShoppingCartItem,
        currentPage: Int,
    ) {
        binding.product = item
        binding.page = currentPage
    }
}
