package woowacourse.shopping.ui.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.cart.CartProduct

class CartAdapter(
    private var items: List<CartProduct>,
    private val cartClickListener: CartClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        return CartViewHolder.create(parent, cartClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(cartProducts: List<CartProduct>) {
        val deletedItem = this.items.firstOrNull { product -> !cartProducts.contains(product) }
        val startPosition = deletedItem?.let { this.items.indexOf(deletedItem) } ?: 0
        val itemCount = this.items.size - startPosition

        if (isLastItem(startPosition, itemCount)) notifyItemChanged(0)
        else notifyItemRangeChanged(startPosition, itemCount)
        
        items = cartProducts
    }

    private fun isLastItem(startPosition: Int, itemCount: Int): Boolean {
        return startPosition == 0 && itemCount == 1
    }
}
