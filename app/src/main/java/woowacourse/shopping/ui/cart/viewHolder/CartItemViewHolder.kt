package woowacourse.shopping.ui.cart.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.cart.CartItemType

sealed class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(cartItemType: CartItemType)
}
