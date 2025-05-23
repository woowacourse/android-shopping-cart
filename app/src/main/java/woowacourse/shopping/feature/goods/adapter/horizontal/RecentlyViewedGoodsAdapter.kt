package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.goods.adapter.vertical.GoodsClickListener

class RecentlyViewedGoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.Adapter<RecentlyViewedGoodsViewHolder>() {
    val items: MutableList<Goods> =
        mutableListOf()

    fun setItems(newItems: List<Goods>) {
        items.clear()
        items.addAll(newItems)
        @Suppress("NotifyDataSetChanged")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedGoodsViewHolder = RecentlyViewedGoodsViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: RecentlyViewedGoodsViewHolder,
        position: Int,
    ) {
        holder.binding.goods = items[position]
    }

    override fun getItemCount(): Int = items.size
}
