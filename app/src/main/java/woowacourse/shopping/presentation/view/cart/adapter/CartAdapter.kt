package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CartItemUiModel
import woowacourse.shopping.presentation.ui.layout.QuantityChangeListener

class CartAdapter(
    initialProducts: List<CartItemUiModel> = emptyList(),
    private val eventListener: CartEventListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val products = initialProducts.toMutableList()

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder = CartViewHolder.from(parent, eventListener)

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateQuantityAt(
        productId: Long,
        quantity: Int,
    ) {
        val oldItem = products.find { it.product.id == productId } ?: return
        val newItem = oldItem.copy(quantity = quantity)
        val position = products.indexOf(oldItem)
        products[position] = newItem

        notifyItemChanged(position)
    }

    fun updateItemsManually(newProducts: List<CartItemUiModel>) {
        removeMissingItems(newProducts)
        addNewItems(newProducts)
    }

    private fun removeMissingItems(newProducts: List<CartItemUiModel>) {
        val iterator = products.listIterator()

        while (iterator.hasNext()) {
            val item = iterator.next()
            if (newProducts.none { it.product.id == item.product.id }) {
                val index = iterator.previousIndex()
                iterator.remove()
                notifyItemRemoved(index)
            }
        }
    }

    private fun addNewItems(newProducts: List<CartItemUiModel>) {
        newProducts.forEach { newItem ->
            val exists = products.any { it.product.id == newItem.product.id }
            if (!exists) {
                products.add(newItem)
                notifyItemInserted(itemCount - 1)
            }
        }
    }

    interface CartEventListener : QuantityChangeListener {
        fun onProductDeletion(cartItem: CartItemUiModel)
    }
}
