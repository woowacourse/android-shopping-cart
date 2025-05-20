package woowacourse.shopping.view.cart.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.Product

class CartAdapter(
    private val items: MutableList<Product> = mutableListOf(),
    private val handler: CartAdapterEventHandler,
) : RecyclerView.Adapter<CartViewHolder>() {
    fun submitList(newItems: List<Product>) {
        val oldSize = items.size
        items.clear()
        items.addAll(newItems)

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
        return CartViewHolder(binding, handler)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
