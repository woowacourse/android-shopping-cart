package woowacourse.shopping.feature.goods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.domain.model.Goods

class GoodsAdapter(
    private val goodsClickListener: GoodsViewHolder.GoodsClickListener,
) : RecyclerView.Adapter<GoodsViewHolder>() {
    private val items: MutableList<Goods> = mutableListOf()

    fun setItems(newItems: List<Goods>) {
        val oldItems = items.toList()
        items.clear()
        items.addAll(newItems)

        if (newItems.size > oldItems.size) {
            notifyItemRangeInserted(oldItems.size, newItems.size - oldItems.size)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGoodsBinding.inflate(inflater, parent, false)
        return GoodsViewHolder(binding, goodsClickListener)
    }

    override fun onBindViewHolder(
        holder: GoodsViewHolder,
        position: Int,
    ) {
        val item: Goods = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}
