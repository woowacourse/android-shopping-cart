package woowacourse.shopping.view.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.view.main.vm.state.ProductState

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val handler: Handler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProductState) {
        binding.model = item
        binding.eventHandler = handler
    }

    interface Handler {
        fun onClickDeleteItem(cartId: Long)
    }
}
