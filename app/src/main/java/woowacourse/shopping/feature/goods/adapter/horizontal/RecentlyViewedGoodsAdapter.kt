package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.goods.adapter.vertical.GoodsClickListener

class RecentlyViewedGoodsAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.Adapter<RecentlyViewedGoodsViewHolder>() {
    val items: MutableList<Goods> =
        mutableListOf()

    fun setItems(newItems: List<Goods>) {
        val oldItems = items.toList()
        items.clear()
        items.addAll(newItems)
        if (oldItems.isEmpty()) {
            @Suppress("NotifyDataSetChanged")
            notifyDataSetChanged()
        } else if (newItems.size > oldItems.size) {
            notifyItemRangeInserted(0, newItems.size - oldItems.size)
        } else if (newItems.size == oldItems.size) {
            oldItems.zip(newItems).forEachIndexed { index, (oldItem, newItem) ->
                if (oldItem != newItem) {
                    notifyItemChanged(index)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedGoodsViewHolder = RecentlyViewedGoodsViewHolder.from(parent, goodsClickListener, lifecycleOwner)

    override fun onBindViewHolder(
        holder: RecentlyViewedGoodsViewHolder,
        position: Int,
    ) {
        holder.binding.goods = items[position]
    }

    override fun getItemCount(): Int = items.size
}
