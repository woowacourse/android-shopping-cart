package woowacourse.shopping.view.cart.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.Product

class CartAdapter(
    private val items: MutableList<Product> = mutableListOf(),
    private val onClickDelete: (Long) -> Unit,
) :
    RecyclerView.Adapter<CartViewHolder>() {
    fun submitList(newItems: List<Product>) {
        val lastPosition = newItems.size
        items.addAll(newItems)
        notifyItemRangeChanged(lastPosition, newItems.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(
            binding,
            object : CartAdapterEventHandler {
                override fun onClickDeleteItem(id: Long) {
                    onClickDelete(id)
                    val position = items.indexOfFirst { it.id == id }
                    items.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, getItemCount() - position)
                }
            },
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
