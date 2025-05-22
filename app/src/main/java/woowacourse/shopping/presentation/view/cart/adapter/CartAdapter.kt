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

    fun updateItemsManually(newProducts: List<CartItemUiModel>) {
        removeMissingItems(newProducts)
        updateChangedItems(newProducts)
        addNewItems(newProducts)
    }

    private fun removeMissingItems(newProducts: List<CartItemUiModel>) {
        val iterator = products.listIterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (newProducts.none { it.productId == item.productId }) {
                val index = iterator.previousIndex()
                iterator.remove()
                notifyItemRemoved(index)
            }
        }
    }

    private fun updateChangedItems(newProducts: List<CartItemUiModel>) {
        newProducts.forEach { newItem ->
            val oldIndex = products.indexOfFirst { it.productId == newItem.productId }
            if (oldIndex != -1 && products[oldIndex] != newItem) {
                products[oldIndex] = newItem
                notifyItemChanged(oldIndex)
            }
        }
    }

    private fun addNewItems(newProducts: List<CartItemUiModel>) {
        newProducts.forEach { newItem ->
            val exists = products.any { it.productId == newItem.productId }
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
