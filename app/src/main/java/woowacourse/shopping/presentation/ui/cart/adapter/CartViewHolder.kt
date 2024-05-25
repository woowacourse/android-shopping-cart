package woowacourse.shopping.presentation.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.presentation.ui.cart.CartHandler

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val cartHandler: CartHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProductListItem.ShoppingProductItem) {
        binding.cartHandler = cartHandler
        binding.product = item
    }
}
