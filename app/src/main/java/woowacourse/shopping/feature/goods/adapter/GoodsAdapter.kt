package woowacourse.shopping.feature.goods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.databinding.ItemHistoryContainerBinding
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.feature.goods.adapter.history.HistoryContainerViewHolder

class GoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<Any> = mutableListOf()
    private var hasNextPage: Boolean = true

    fun setItems(newItems: List<Any>) {
        val oldHistory = items.firstOrNull()?.takeIf { it is List<*> && it.all { h -> h is Cart } } as? List<*>
        val newHistory = newItems.firstOrNull()?.takeIf { it is List<*> && it.all { h -> h is Cart } } as? List<*>

        if (oldHistory != null && newHistory != null && oldHistory != newHistory) {
            items[0] = newHistory
            notifyItemChanged(0)
        }

        newItems.forEachIndexed { index, newItem ->
            val oldItem = items.getOrNull(index)
            if (
                oldItem is Cart &&
                newItem is Cart &&
                oldItem.goods.id == newItem.goods.id &&
                oldItem.quantity != newItem.quantity
            ) {
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

    fun setHasNextPage(value: Boolean) {
        if (hasNextPage != value) {
            hasNextPage = value
            notifyItemInserted(items.size)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when {
            position < items.size -> {
                val item = items[position]
                when {
                    item is List<*> && item.all { it is Cart } -> ItemViewType.HISTORY.type
                    item is Cart -> ItemViewType.GOODS.type
                    else -> ItemViewType.LOAD_MORE.type
                }
            }
            else -> ItemViewType.LOAD_MORE.type
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ItemViewType.from(viewType)) {
            ItemViewType.HISTORY -> {
                val binding = ItemHistoryContainerBinding.inflate(inflater, parent, false)
                HistoryContainerViewHolder(binding, goodsClickListener)
            }
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
            is HistoryContainerViewHolder -> {
                val item = items[position]
                if (item is List<*> && item.all { it is Cart }) {
                    holder.bind(item as List<Cart>)
                }
            }
            is GoodsViewHolder -> {
                val item = items[position]
                if (item is Cart) {
                    holder.bind(item)
                }
            }
            is LoadMoreViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int = items.size + if (hasNextPage) 1 else 0
}
