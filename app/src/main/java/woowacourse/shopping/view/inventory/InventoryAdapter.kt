package woowacourse.shopping.view.inventory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.model.InventoryItem
import woowacourse.shopping.view.model.InventoryItem.ProductUiModel
import woowacourse.shopping.view.model.InventoryItem.ShowMoreButton
import woowacourse.shopping.view.model.InventoryItemType

class ProductsAdapter(
    private val handler: InventoryEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<InventoryItem> = listOf()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type.id
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = items[position]
        when (holder) {
            is ProductViewHolder -> holder.bind(item as ProductUiModel)
            is ShowMoreViewHolder -> holder.bind(item as ShowMoreButton)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            InventoryItemType.PRODUCT.id -> ProductViewHolder(parent, handler)
            InventoryItemType.SHOW_MORE_BUTTON.id -> ShowMoreViewHolder(parent, handler)
            else -> throw IllegalStateException()
        }
    }

    fun updateProducts(newProducts: List<ProductUiModel>) {
        items += newProducts
    }
}

interface InventoryEventHandler {
    fun onProductSelected(product: ProductUiModel)

    fun onLoadMoreProducts(page: Int)
}
