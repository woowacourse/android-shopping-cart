package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.view.cart.viewholder.CartViewHolder

class CartAdapter(
    items: List<CartProductModel>,
    private val onCloseClick: (Long) -> Unit,
    private val onCheckedChangeListener: (Long, Boolean, List<Boolean>) -> Unit,
    private val onCountChanged: (Long, Int) -> Unit
) : RecyclerView.Adapter<CartViewHolder>() {
    private val items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            parent,
            onCloseClick = {
                onCloseClick(items[it].product.id)
            },
            onCheckedChangeListener = { position, isChecked ->
                items[position] = items[position].copy(isChecked = isChecked)
                onCheckedChangeListener(
                    items[position].product.id,
                    isChecked,
                    items.map { it.isChecked }
                )
            },
            { position, count ->
                items[position] = items[position].copy(count = count)
                onCountChanged(items[position].product.id, count)
            }
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItemsChecked(isChecked: Boolean) {
        items.forEachIndexed { index, _ ->
            items[index] = items[index].copy(isChecked = isChecked)
        }
        notifyItemRangeChanged(0, items.size)
    }
}
