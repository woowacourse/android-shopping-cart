package woowacourse.shopping.presentation.goods.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.util.QuantitySelectorListener

class GoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
    private val quantitySelectorListener: QuantitySelectorListener,
) : RecyclerView.Adapter<GoodsViewHolder>() {
    private val items: MutableList<Goods> = mutableListOf()

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

    fun updateItems(goods: List<Goods>) {
        if (goods.size == items.size) {
            handleSameSizeUpdate(goods)
        } else if (goods.size > items.size) {
            handleAddition(goods)
        }
    }

    private fun handleSameSizeUpdate(goods: List<Goods>) {
        goods.forEachIndexed { index, item ->
            if (items[index] != item) {
                items[index] = item
                notifyItemChanged(index)
            }
        }
    }

    private fun handleAddition(goods: List<Goods>) {
        val fromIndex = items.size
        val itemCount = goods.size - items.size
        items.clear()
        items.addAll(goods)
        notifyItemRangeInserted(fromIndex, itemCount)
    }
}
