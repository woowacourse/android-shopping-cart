package woowacourse.shopping.presentation.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.ui.cart.CartActionHandler
import woowacourse.shopping.presentation.ui.cart.viewholder.CartViewHolder

class CartAdapter(
    private val actionHandler: CartActionHandler,
) : ListAdapter<CartItem, CartViewHolder>(CartItemDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val cartItem = getItem(position)
        holder.bind(cartItem, actionHandler)
    }
}

class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(
        oldItem: CartItem,
        newItem: CartItem,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CartItem,
        newItem: CartItem,
    ): Boolean {
        return oldItem == newItem
    }
}
