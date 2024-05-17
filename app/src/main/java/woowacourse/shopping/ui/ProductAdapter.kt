package woowacourse.shopping.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.Product

class ProductAdapter(
    items: List<ProductItem> = emptyList(),
    private val onClickProduct: (Product) -> Unit,
    private val onClickReadMore: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<ProductItem> = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ProductItem.PRODUCT_TYPE -> ProductViewHolder.from(parent, onClickProduct)
            ProductItem.READ_MORE_TYPE -> ReadMoreViewHolder.from(parent, onClickReadMore)

            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ProductItem.ProductType -> (holder as ProductViewHolder).bind(item.product)
            is ProductItem.ReadMoreType -> Unit
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun submitList(newItems: List<Product>) {
        val insertedItems = newItems
            .map(ProductItem::ProductType) + ProductItem.ReadMoreType
        items.clear()
        items.addAll(insertedItems)
        notifyItemRangeInserted(items.size, insertedItems.size)
    }
}
