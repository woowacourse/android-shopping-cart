package woowacourse.shopping.feature.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.CartProductState

class CartProductListAdapter(
    private var cartProductStates: List<CartProductState> = listOf(),
    private val onCartProductDeleteClick: (CartProductState) -> Unit,
    private val onCountMinusClick: (CartProductState) -> Unit,
    private val onCountPlusClick: (CartProductState) -> Unit
) : RecyclerView.Adapter<CartProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder.createInstance(
            parent, onCartProductDeleteClick, onCountMinusClick, onCountPlusClick
        )
    }

    override fun getItemCount(): Int {
        return cartProductStates.size
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(cartProductStates[position])
    }

    fun setItems(cartProducts: List<CartProductState>) {
        this.cartProductStates = cartProducts.toList()
        notifyDataSetChanged()
    }
}
