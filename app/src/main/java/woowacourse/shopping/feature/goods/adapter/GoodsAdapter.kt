package woowacourse.shopping.feature.goods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.domain.model.Cart

class GoodsAdapter(
    private val goodsClickListener: GoodsViewHolder.GoodsClickListener,
) : RecyclerView.Adapter<GoodsViewHolder>() {
    private val items: MutableList<Cart> = mutableListOf()

    fun setItems(newItems: List<Cart>) {
        newItems.forEachIndexed { index, newItem ->
            val oldItem = items.getOrNull(index)
            if (oldItem != null && oldItem.goods.id == newItem.goods.id && oldItem.quantity != newItem.quantity) {
                items[index] = newItem
                notifyItemChanged(index)
            }
        }

        if (items.size != newItems.size) {
            val positionStart = items.size
            val newItemCount = newItems.size

            items.clear()
            items.addAll(newItems)
            notifyItemRangeInserted(positionStart, newItemCount)
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
        val item: Cart = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}
