package woowacourse.shopping.feature.goods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.databinding.ItemHistoryContainerBinding
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.feature.goods.adapter.history.HistoryContainerViewHolder
import woowacourse.shopping.feature.model.GoodsItem

class GoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
) : ListAdapter<GoodsItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private var hasNextPage: Boolean = true

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<GoodsItem>() {
                override fun areItemsTheSame(
                    oldItem: GoodsItem,
                    newItem: GoodsItem,
                ): Boolean =
                    when {
                        oldItem is GoodsItem.History && newItem is GoodsItem.History ->
                            oldItem.histories == newItem.histories
                        oldItem is GoodsItem.Product && newItem is GoodsItem.Product ->
                            oldItem.cart.goods.id == newItem.cart.goods.id
                        oldItem is GoodsItem.LoadMore && newItem is GoodsItem.LoadMore -> true
                        else -> false
                    }

                override fun areContentsTheSame(
                    oldItem: GoodsItem,
                    newItem: GoodsItem,
                ): Boolean = oldItem == newItem
            }
    }

    fun setItems(newItems: List<GoodsItem>) {
        val finalItems = if (hasNextPage) newItems + GoodsItem.LoadMore else newItems
        submitList(finalItems)
    }

    fun setHasNextPage(value: Boolean) {
        if (hasNextPage != value) {
            hasNextPage = value
            submitList(
                currentList.filter { it !is GoodsItem.LoadMore }.let {
                    if (hasNextPage) it + GoodsItem.LoadMore else it
                },
            )
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is GoodsItem.History -> ItemViewType.HISTORY.type
            is GoodsItem.Product -> ItemViewType.GOODS.type
            is GoodsItem.LoadMore -> ItemViewType.LOAD_MORE.type
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
        when (val item = getItem(position)) {
            is GoodsItem.History -> {
                if (holder is HistoryContainerViewHolder) {
                    holder.bind(item.histories)
                }
            }
            is GoodsItem.Product -> {
                if (holder is GoodsViewHolder) {
                    holder.bind(item.cart)
                }
            }
            is GoodsItem.LoadMore -> {
                if (holder is LoadMoreViewHolder) {
                    holder.bind()
                }
            }
        }
    }
}
