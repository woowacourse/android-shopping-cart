package woowacourse.shopping.presentation.goods.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ShoppingCartItemUiModel
import woowacourse.shopping.presentation.util.QuantityClickListener

class GoodsAdapter(
    private val quantityClickListener: QuantityClickListener,
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.Adapter<GoodsViewHolder>() {
    private var items: List<ShoppingCartItemUiModel> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GoodsViewHolder {
        return GoodsViewHolder(parent, quantityClickListener, goodsClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: GoodsViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<ShoppingCartItemUiModel>) {
        val oldItems = items
        val mutableItems = oldItems.toMutableList()

        newItems.forEachIndexed { index, newItem ->
            val oldItem = oldItems.getOrNull(index)

            when {
                oldItem == null -> {
                    mutableItems.add(newItem)
                    notifyItemInserted(index)
                }

                oldItem != newItem -> {
                    mutableItems[index] = newItem
                    notifyItemChanged(index)
                }
            }
        }

        items = mutableItems
    }
}
