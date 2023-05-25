package woowacourse.shopping.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.cart.uistate.CartItemUIState

class CartListAdapter(
    private val onClickCloseButton: (Long) -> Unit,
    private val onClickCheckBox: (Long, Boolean) -> Unit,
    private val onClickPlus: (Long) -> Unit,
    private val onClickMinus: (Long) -> Unit,
) : RecyclerView.Adapter<CartListViewHolder>() {

    private val cartItems: MutableList<CartItemUIState> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        return CartListViewHolder.create(
            parent,
            onClickCloseButton,
            onClickCheckBox,
            onClickPlus,
            onClickMinus
        )
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCartItems(cartItems: List<CartItemUIState>) {
        this.cartItems.clear()
        this.cartItems.addAll(cartItems)
        notifyDataSetChanged()
    }
}
