package woowacourse.shopping.feature.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods

class CartAdapter(
    private val cartClickListener: CartViewHolder.CartClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val items: MutableList<Goods> = mutableListOf()

    fun setItems(newItems: List<Goods>) {
        val maxSize = maxOf(items.size, newItems.size)

        items.clear()
        items.addAll(newItems)
        notifyItemRangeChanged(ITEM_START_POSITION, maxSize)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder = CartViewHolder.from(parent, cartClickListener)

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item: Goods = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    companion object {
        private const val ITEM_START_POSITION = 0
    }
}
