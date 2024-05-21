package woowacourse.shopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Cart

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val itemRemoveClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(cart: Cart) {
        binding.cart = cart
        binding.ivRemove.setOnClickListener {
            itemRemoveClickListener(cart.id)
        }
    }
}
