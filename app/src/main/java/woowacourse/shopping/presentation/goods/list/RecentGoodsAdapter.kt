package woowacourse.shopping.presentation.goods.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods

class RecentGoodsAdapter(
    private val clickListener: GoodsClickListener,
) : RecyclerView.Adapter<RecentGoodsViewHolder>() {
    private var items: List<Goods> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentGoodsViewHolder {
        return RecentGoodsViewHolder(parent, clickListener)
    }

    override fun onBindViewHolder(
        holder: RecentGoodsViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    fun updateItems(newItems: List<Goods>) {
        items = newItems
        notifyDataSetChanged()
    }
}
