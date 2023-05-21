package woowacourse.shopping.ui.cart.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.UiCartProduct
import woowacourse.shopping.ui.cart.listener.CartClickListener

class CartViewHolder(
    parent: ViewGroup,
    cartClickListener: CartClickListener,
) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
) {
    private val binding = ItemCartBinding.bind(itemView)

    init {
        binding.cartClickListener = cartClickListener
    }

    fun bind(item: UiCartProduct) {
        binding.cartProduct = item
    }
}
