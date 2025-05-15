package woowacourse.shopping.view.shoppingcart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.Product

class ShoppingCartViewHolder(
    private val binding: ItemShoppingCartProductBinding,
    handler: ShoppingCartEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(item: Product) {
        binding.product = item
    }
}
