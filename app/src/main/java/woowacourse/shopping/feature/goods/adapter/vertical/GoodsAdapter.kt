package woowacourse.shopping.feature.goods.adapter.vertical

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.feature.QuantityChangeListener

class GoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
    private val quantityChangeListener: QuantityChangeListener,
) : RecyclerView.Adapter<GoodsViewHolder>() {
    private val items: MutableList<CartItem> = mutableListOf()

    fun setItems(newItems: List<CartItem>) {
        val oldItems = items.toList()
        items.clear()
        items.addAll(newItems)
        if (oldItems.isEmpty()) {
            @Suppress("NotifyDataSetChanged")
            notifyDataSetChanged()
        } else if (newItems.size > oldItems.size) {
            notifyItemRangeInserted(oldItems.size, newItems.size - oldItems.size)
        } else if (newItems.size == oldItems.size) {
            oldItems.zip(newItems).forEachIndexed { index, (oldItem, newItem) ->
                if (oldItem != newItem) {
                    updateItemQuantity(index)
                }
            }
        }
    }

    private fun updateItemQuantity(position: Int) {
        notifyItemChanged(position, "quantity_changed")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GoodsViewHolder = GoodsViewHolder.from(parent, goodsClickListener, quantityChangeListener)

    override fun onBindViewHolder(
        holder: GoodsViewHolder,
        position: Int,
    ) {
        val item: CartItem = items[position]
        holder.bind(item)
    }

    override fun onBindViewHolder(
        holder: GoodsViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val payload = payloads[0]
            if (payload == "quantity_changed") {
                holder.binding.cartItem = items[position]
                holder.binding.quantitySelector.cartItem = items[position]
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
