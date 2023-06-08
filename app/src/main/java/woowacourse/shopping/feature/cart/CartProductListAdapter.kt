package woowacourse.shopping.feature.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.cart.model.CartProductState

class CartProductListAdapter(
    private var cartProductStates: List<CartProductState> = listOf(),
    private val onCartProductDeleteClick: (cartProductState: CartProductState) -> Unit,
    private val plusCount: (cartProductState: CartProductState) -> Unit,
    private val minusCount: (cartProductState: CartProductState) -> Unit,
    private val updateChecked: (productId: Int, checked: Boolean) -> Unit
) : RecyclerView.Adapter<CartProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder.createInstance(
            parent, onCartProductDeleteClick, plusCount, minusCount, updateChecked
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
