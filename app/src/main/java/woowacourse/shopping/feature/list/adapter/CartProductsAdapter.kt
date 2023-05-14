package woowacourse.shopping.feature.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.list.item.CartProductItem
import woowacourse.shopping.feature.list.viewholder.CartProductViewHolder

class CartProductsAdapter(
    private var items: List<CartProductItem> = listOf(),
    private val onDeleteItem: (CartProductItem) -> Unit,
) : RecyclerView.Adapter<CartProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(items[position], onDeleteItem)
    }

    fun setItems(items: List<CartProductItem>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
