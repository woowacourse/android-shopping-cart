package woowacourse.shopping.presentation.goods.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.GoodsUiModel

class GoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.Adapter<GoodsViewHolder>() {
    private var items: List<GoodsUiModel> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GoodsViewHolder {
        return GoodsViewHolder(parent, goodsClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: GoodsViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    fun loadMoreItems(goods: List<GoodsUiModel>) {
        val fromIndex = items.size
        items = goods
        notifyItemRangeChanged(fromIndex, items.size)
    }
}
