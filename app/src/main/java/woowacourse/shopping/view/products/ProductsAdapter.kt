package woowacourse.shopping.view.products

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.cart.CartItem

class ProductsAdapter(
    private val productEventListener: ProductEventListener,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
    private val cartItems: MutableList<CartItem> = mutableListOf(),
    private val openSelectorIds: MutableSet<Long> = mutableSetOf(),
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder =
        ProductViewHolder.from(
            parent,
            productEventListener,
            quantitySelectButtonListener,
        )

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        val item = cartItems[position]
        holder.bind(item, openSelectorIds.contains(item.product.id))
    }

    fun notifyProductsChanged(list: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    fun notifyQuantitySelectViewChanged(productId: Long) {
        if (openSelectorIds.contains(productId)) {
            openSelectorIds.remove(productId)
        } else {
            openSelectorIds.add(productId)
        }
        val index = cartItems.indexOfFirst { it.product.id == productId }
        if (index != -1) notifyItemChanged(index)
    }
}
