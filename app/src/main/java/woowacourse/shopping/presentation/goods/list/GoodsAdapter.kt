package woowacourse.shopping.presentation.goods.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.util.QuantitySelectorListener

class GoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
    private val quantitySelectorListener: QuantitySelectorListener,
) : RecyclerView.Adapter<GoodsViewHolder>() {
    private var items: List<Goods> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GoodsViewHolder {
        return GoodsViewHolder(parent, goodsClickListener, quantitySelectorListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: GoodsViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    fun changeGoods(goods: List<Goods>) {
        if (goods.size > items.size) {
            val fromIndex = items.size
            val itemCount = goods.size - items.size
            items = goods
            notifyItemRangeInserted(fromIndex, itemCount)
        } else {
            items = goods
            notifyItemRangeChanged(0, items.size)
        }
    }

    fun changeQuantity(goodsId: Int) {
        val position = items.indexOfFirst { it.id == goodsId }
        notifyItemChanged(position)
    }
}
