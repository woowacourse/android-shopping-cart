package woowacourse.shopping.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.Goods

class CartAdapter(
    private val cartClickListener: CartViewHolder.CartClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val items: MutableList<Goods> = mutableListOf()

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
    }

    fun setItems(newItems: List<Goods>) {
        val oldItems = items.toList()
        items.clear()
        items.addAll(newItems)

        if (newItems.size < oldItems.size) {
            notifyItemRangeRemoved(newItems.size, oldItems.size - newItems.size)
        }
        newItems.forEachIndexed { index, newGoods ->
            if (index < oldItems.size && newGoods != oldItems[index]) {
                notifyItemChanged(index)
            }
        }
        if (newItems.size > oldItems.size) {
            notifyItemRangeInserted(oldItems.size, newItems.size - oldItems.size)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        binding.cartClickListener = cartClickListener
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item: Goods = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}
