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

    fun addLatestGoods(newItems: List<Goods>) {
        items.clear()
        items.addAll(newItems)

        notifyItemRangeChanged(POSITION_START, items.size)
    }

    companion object {
        private const val POSITION_START: Int = 0
    }
}
