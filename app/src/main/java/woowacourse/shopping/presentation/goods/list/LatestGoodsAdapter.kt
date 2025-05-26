package woowacourse.shopping.presentation.goods.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods

class LatestGoodsAdapter(
    private val latestGoodsClickListener: LatestGoodsClickListener,
) : RecyclerView.Adapter<LatestGoodsViewHolder>() {
    private val items: MutableList<Goods> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LatestGoodsViewHolder {
        return LatestGoodsViewHolder(parent, latestGoodsClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: LatestGoodsViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    fun updateLatestGoods(newItems: List<Goods>) {
        val oldItems = items.toList()

        items.clear()
        items.addAll(newItems)

        oldItems.forEachIndexed { index, item ->
            if (newItems[index] != item) notifyItemChanged(index)
        }

        if (oldItems.size < newItems.size) {
            notifyItemRangeInserted(oldItems.size, newItems.size - oldItems.size)
        }
    }
}
