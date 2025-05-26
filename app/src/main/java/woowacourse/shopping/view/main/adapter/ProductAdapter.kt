package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.main.event.ProductsAdapterEventHandler
import woowacourse.shopping.view.util.QuantitySelectorEventHandler

class ProductAdapter(
    private var items: List<Cart> = listOf(),
    private val quantitySelectorEventHandler: QuantitySelectorEventHandler,
    private val handler: ProductsAdapterEventHandler,
) : RecyclerView.Adapter<ProductViewHolder>() {
    fun submitList(newItems: List<Cart>) {
        val lastPosition = items.size
        val newItemsSize = newItems.size
        val subList = newItems.subList(lastPosition, newItems.size)
        val updatedItems = newItems.subtract(items.toSet()).toList()
        if (updatedItems.size == 1) {
            val updateItemIndex = newItems.indexOf(updatedItems[0])
            items = newItems
            notifyItemChanged(updateItemIndex)
        } else {
            items = items + subList
            notifyItemRangeInserted(lastPosition, newItemsSize - lastPosition)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(bind, quantitySelectorEventHandler, handler)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
