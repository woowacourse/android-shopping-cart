package woowacourse.shopping.feature.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.list.viewholder.CartProductViewHolder

class CartProductsAdapter(
    private val items: MutableList<CartProductItem> = mutableListOf(),
    private val onDeleteItem: (CartProductItem) -> Unit,
    private val onCheckItem: (CartProductItem) -> Unit,
    private val onPlusItem: (CartProductItem) -> Unit,
    private val onMinusItem: (CartProductItem) -> Unit,
) : RecyclerView.Adapter<CartProductViewHolder>() {
    private var isSelected: List<Boolean> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder(
            parent,
            onDeleteItem = { position -> onDeleteItem(items[position]) },
            onCheckItem = { position -> onCheckItem(items[position]) },
            onPlusItem = { position -> onPlusItem(items[position]) },
            onMinusItem = { position -> onMinusItem(items[position]) },
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(items[position], isSelected[position])
    }

    fun setItems(
        items: List<CartProductItem>,
        isSelected: List<Boolean> = List(items.size) { false },
    ) {
        this.items.clear()
        this.items.addAll(items)
        this.isSelected = isSelected
        notifyDataSetChanged()
    }
}
