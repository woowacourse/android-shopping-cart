package woowacourse.shopping.view.cart.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.cart.event.CartAdapterEventHandler
import woowacourse.shopping.view.util.QuantitySelectorEventHandler

class CartAdapter(
    private var items: List<Cart> = listOf(),
    private val quantitySelectorEventHandler: QuantitySelectorEventHandler,
    private val handler: CartAdapterEventHandler,
) : RecyclerView.Adapter<CartViewHolder>() {
    fun submitList(newItems: List<Cart>) {
        val oldSize = items.size
        val updatedItem =
            newItems.find { newItem -> items.any { it.product == newItem.product && it.quantity != newItem.quantity } }
        items = newItems
        if (updatedItem != null) {
            val updateItemIndex = newItems.indexOf(updatedItem)
            notifyItemChanged(updateItemIndex)
            return
        }
        if (newItems.size == oldSize) {
            notifyItemRangeChanged(0, newItems.size)
        } else {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, newItems.size)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, quantitySelectorEventHandler, handler)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
