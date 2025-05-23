package woowacourse.shopping.feature.goods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.domain.model.Cart

class GoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<Cart> = mutableListOf()

    fun setItems(newItems: List<Cart>) {
        newItems.forEachIndexed { index, newItem ->
            val oldItem = items.getOrNull(index)
            if (oldItem != null && oldItem.goods.id == newItem.goods.id && oldItem.quantity != newItem.quantity) {
                items[index] = newItem
                notifyItemChanged(index)
            }
        }

        if (items.size != newItems.size) {
            items.clear()
            items.addAll(newItems)
            notifyItemInserted(items.size - 1)
        }
    }

    fun updateItemQuantity(
        id: Long,
        quantity: Int,
    ) {
        val index = items.indexOfFirst { it.goods.id == id }
        if (index != -1) {
            val oldItem = items[index]
            items[index] = oldItem.copy(quantity = quantity)
            notifyItemChanged(index)
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position < items.size) ItemViewType.GOODS.ordinal else ItemViewType.LOAD_MORE.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ItemViewType.entries[viewType]) {
            ItemViewType.GOODS -> {
                val binding = ItemGoodsBinding.inflate(inflater, parent, false)
                GoodsViewHolder(binding, goodsClickListener)
            }
            ItemViewType.LOAD_MORE -> {
                val binding = ItemLoadMoreBinding.inflate(inflater, parent, false)
                LoadMoreViewHolder(binding, goodsClickListener)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is GoodsViewHolder -> {
                holder.bind(items[position])
            }
            is LoadMoreViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        val count = items.size + 1
        return count
    }
}
