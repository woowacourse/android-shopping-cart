package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.view.ItemCounterListener

class CartAdapter(
    private val eventListener: CartEventListener,
    private val itemCounterListener: ItemCounterListener,
) : ListAdapter<CartItem, CartViewHolder>(cartDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder = CartViewHolder.from(parent, eventListener, itemCounterListener)

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val cartDiffCallback =
            object : DiffUtil.ItemCallback<CartItem>() {
                override fun areItemsTheSame(
                    oldItem: CartItem,
                    newItem: CartItem,
                ): Boolean = oldItem.product.id == newItem.product.id

                override fun areContentsTheSame(
                    oldItem: CartItem,
                    newItem: CartItem,
                ): Boolean = oldItem == newItem
            }
    }

    interface CartEventListener {
        fun onProductDeletion(cartItem: CartItem)
    }
}
