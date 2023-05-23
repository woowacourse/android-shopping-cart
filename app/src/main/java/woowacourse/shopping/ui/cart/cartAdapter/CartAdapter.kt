package woowacourse.shopping.ui.cart.cartAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.CartProductViewHolder
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.CartViewHolder
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.NavigationViewHolder

class CartAdapter(private val cartListener: CartListener) :
    ListAdapter<CartItemType, CartViewHolder>(CartDiffCallback()) {

    private val cartItems = mutableListOf<CartItemType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return when (viewType) {
            CartItemType.TYPE_ITEM -> CartProductViewHolder.from(parent, cartListener)
            CartItemType.TYPE_FOOTER -> NavigationViewHolder.from(parent, cartListener)
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        return holder.bind(cartItems[position])
    }

    override fun getItemViewType(position: Int): Int {
        return cartItems[position].viewType
    }

    fun submitList(cartProducts: List<CartProductUIModel>, pageUIModel: PageUIModel) {
        cartItems.clear()
        cartItems.addAll(cartProducts.map { CartItemType.Cart(it) })
        cartItems.add(CartItemType.Navigation(pageUIModel))
        submitList(cartItems.toList())
    }

    companion object {
        class CartDiffCallback : DiffUtil.ItemCallback<CartItemType>() {
            override fun areItemsTheSame(oldItem: CartItemType, newItem: CartItemType): Boolean {
                return oldItem.viewType == newItem.viewType
            }

            override fun areContentsTheSame(oldItem: CartItemType, newItem: CartItemType): Boolean {
                return oldItem == newItem
            }
        }
    }
}
