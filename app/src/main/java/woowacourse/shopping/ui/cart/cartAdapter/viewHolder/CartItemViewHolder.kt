package woowacourse.shopping.ui.cart.cartAdapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType

sealed class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(cartItemType: CartItemType)
}
