package woowacourse.shopping.view.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.product.Product

class CartAdapter(
    private val itemsInCart: MutableList<Product> = mutableListOf(),
    private val onProductRemoveClickListener: (Product) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder = CartViewHolder.from(parent)

    override fun getItemCount(): Int = itemsInCart.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.removeProductButton.setOnClickListener {
            onProductRemoveClickListener(itemsInCart[position])
        }
        holder.bind(itemsInCart[position])
    }

    fun updateProductsView(items: List<Product>) {
        val oldSize = itemsInCart.size
        itemsInCart.clear()
        notifyItemRangeRemoved(0, oldSize)

        itemsInCart.addAll(items)
        notifyItemRangeInserted(0, items.size)
    }
}
