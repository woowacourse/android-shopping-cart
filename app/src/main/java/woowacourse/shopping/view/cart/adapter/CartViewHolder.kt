package woowacourse.shopping.view.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.view.cart.vm.CartState

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val handler: CartAdapterEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CartState) {
        binding.model = item
        binding.eventHandler = handler
    }
}
