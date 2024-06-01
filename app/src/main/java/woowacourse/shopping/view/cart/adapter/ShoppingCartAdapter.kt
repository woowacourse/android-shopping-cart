package woowacourse.shopping.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.CountActionHandler
import woowacourse.shopping.view.cart.ShoppingCartActionHandler
import woowacourse.shopping.view.cart.adapter.viewholder.ShoppingCartViewHolder

class ShoppingCartAdapter(
    private val shoppingCartActionHandler: ShoppingCartActionHandler,
    private val countActionHandler: CountActionHandler,
) : ListAdapter<CartItem, ShoppingCartViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val view =
            ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(view, shoppingCartActionHandler, countActionHandler)
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<CartItem>() {
                override fun areItemsTheSame(
                    oldCartItem: CartItem,
                    newCartItem: CartItem,
                ): Boolean {
                    return oldCartItem.id == newCartItem.id
                }

                override fun areContentsTheSame(
                    oldCartItem: CartItem,
                    newCartItem: CartItem,
                ): Boolean {
                    return oldCartItem == newCartItem
                }
            }
    }
}
