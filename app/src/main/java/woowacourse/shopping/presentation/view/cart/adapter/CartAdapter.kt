package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CartItemUiModel

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
        addNewItems(newProducts)
    }

    private fun removeMissingItems(newProducts: List<CartItemUiModel>) {
        val iterator = products.listIterator()

        while (iterator.hasNext()) {
            val item = iterator.next()
            if (newProducts.none { it.id == item.id }) {
                val index = iterator.previousIndex()
                iterator.remove()
                notifyItemRemoved(index)
            }
        }
    }

    private fun addNewItems(newProducts: List<CartItemUiModel>) {
        newProducts.forEach { newItem ->
            val exists = products.any { it.id == newItem.id }
            if (!exists) {
                products.add(newItem)
                notifyItemInserted(itemCount - 1)
            }
        }
    }

    interface CartEventListener {
        fun onProductDeletion(cartItem: CartItemUiModel)
    }
}
