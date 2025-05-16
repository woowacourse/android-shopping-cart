package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CartItemUiModel
import kotlin.math.max

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

    fun refresh(newProducts: List<CartItemUiModel>) {
        val updatePosition = max(itemCount, newProducts.size)

        products.clear()
        products.addAll(newProducts)
        notifyItemChanged(updatePosition)
    }

    fun replaceAll(newProducts: List<CartItemUiModel>) {
        val previousItemCount = itemCount
        products.clear()
        notifyItemRangeRemoved(STARTING_POSITION, previousItemCount)

        products.addAll(newProducts)
        notifyItemRangeInserted(STARTING_POSITION, itemCount)
    }

    fun removeCartById(id: Long) {
        val index = products.indexOfFirst { it.id == id }
        if (index != -1) {
            products.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    interface CartEventListener {
        fun onProductDeletion(cartItem: CartItemUiModel)
    }

    companion object {
        private const val STARTING_POSITION = 0
    }
}
