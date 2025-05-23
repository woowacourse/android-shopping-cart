package woowacourse.shopping.ui.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.cart.CartProduct

class CartAdapter(
    private val items: MutableList<CartProduct>,
    private val cartClickListener: CartClickListener,
    private val viewModel: CartViewModel
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        return CartViewHolder.create(parent, cartClickListener,viewModel)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(newCartProducts: List<CartProduct>) {
        val deletedItem = this.items.firstOrNull { product -> !newCartProducts.contains(product) }
        val startPosition = deletedItem?.let { this.items.indexOf(deletedItem) } ?: 0
        val itemCount = this.items.size - startPosition

        items.clear()
        notifyItemRangeRemoved(startPosition, itemCount)

        items.addAll(newCartProducts)
        notifyItemRangeInserted(startPosition, itemCount)
    }
}
