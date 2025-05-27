package woowacourse.shopping.view.cart.adatper

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.cart.event.CartAdapterEventHandler
import woowacourse.shopping.view.util.QuantitySelectorEventHandler

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val quantitySelectorEventHandler: QuantitySelectorEventHandler,
    private val handler: CartAdapterEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Cart) {
        binding.cart = item
        binding.quantitySelectorEventHandler = quantitySelectorEventHandler
        binding.cartAdapterEventHandler = handler
    }
}
