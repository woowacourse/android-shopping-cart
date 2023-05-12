package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.cart.viewHolder.CartNavigationViewHolder
import woowacourse.shopping.model.CartUIModel

class CartNavigationAdapter(
    private val cartUIModel: CartUIModel,
    private val onPageUp: () -> Unit,
    private val onPageDown: () -> Unit,
) : RecyclerView.Adapter<CartNavigationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartNavigationViewHolder {
        return CartNavigationViewHolder.from(parent, onPageUp, onPageDown)
    }

    override fun onBindViewHolder(holder: CartNavigationViewHolder, position: Int) {
        holder.bind(cartUIModel)
        return
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
