package woowacourse.shopping.view.cart.adatper

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.cart.event.CartAdapterEventHandler

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val handler: CartAdapterEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Cart) {
        binding.model = item.product
        binding.eventHandler = handler
    }
}
