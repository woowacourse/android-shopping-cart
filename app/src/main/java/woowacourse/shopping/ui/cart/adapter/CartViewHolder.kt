package woowacourse.shopping.ui.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Cart
import woowacourse.shopping.ui.CountButtonClickListener

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val itemRemoveClickListener: (Long) -> Unit,
    private val plusCountClickListener: (Long) -> Unit,
    private val minusCountClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(cart: Cart) {
        binding.cart = cart
        binding.ivRemove.setOnClickListener {
            itemRemoveClickListener(cart.id)
        }
        binding.countButtonClickListener = countButtonClickListener(cart)
    }

    private fun countButtonClickListener(cart: Cart) =
        object : CountButtonClickListener {
            override fun plusCount() {
                plusCountClickListener(cart.id)
            }

            override fun minusCount() {
                minusCountClickListener(cart.id)
            }
        }
}
