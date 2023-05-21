package woowacourse.shopping.ui.cart

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.ui.cart.contract.CartContract
import woowacourse.shopping.ui.cart.viewHolder.CartViewHolder
import woowacourse.shopping.ui.cart.viewHolder.OnCartClickListener

class CartAdapter(
    cartItems: List<CartProductUIModel>,
    private val lifecycleOwner: LifecycleOwner,
    private val presenter: CartContract.Presenter,
    private val onCartClickListener: OnCartClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {

    private var cartItems: MutableList<CartProductUIModel> = cartItems.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder.from(
            parent,
            lifecycleOwner,
            presenter,
            onCartClickListener,
        )
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
