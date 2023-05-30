package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.view.cart.viewholder.CartViewHolder

class CartAdapter(
    private var items: List<CartProductModel>,
    private val onCloseClick: (Long, Int) -> Unit,
    private val onCheckedChangeListener: (Long, Boolean, List<Boolean>) -> Unit,
    private val onCountChanged: (Long, Int) -> Unit
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            parent,
            onCloseClick = {
                onCloseClick(items[it].product.id, items.size)
            },
            onCheckedChangeListener = { position, isChecked ->
                updateItemChecked(position, isChecked)
                onCheckedChangeListener(
                    items[position].product.id,
                    isChecked,
                    items.map { it.isChecked }
                )
            },
            { position, count ->
                updateItemCount(position, count)
                onCountChanged(items[position].product.id, count)
            }
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    private fun updateItemCount(position: Int, count: Int) {
        val updatedItems = items.toMutableList().apply {
            this[position] = this[position].copy(count = count)
        }
        items = updatedItems.toList()
    }

    private fun updateItemChecked(position: Int, isChecked: Boolean) {
        val updatedItems = items.toMutableList().apply {
            this[position] = this[position].copy(isChecked = isChecked)
        }
        items = updatedItems.toList()
    }

    fun updateItemAllChecked(isChecked: Boolean) {
        items = items.map {
            it.copy(isChecked = isChecked)
        }
        notifyItemRangeChanged(0, items.size)
    }
}
