package woowacourse.shopping.view.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.product.Product

class ProductAdapter(
    private val onSelectProduct: (Product) -> Unit,
    private val onLoad: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ProductsItem> = emptyList()

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (ProductsItem.ItemType.from(viewType)) {
            ProductsItem.ItemType.PRODUCT -> ProductViewHolder.of(parent, onSelectProduct)
            ProductsItem.ItemType.MORE -> ProductMoreViewHolder.of(parent, onLoad)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(items[position] as ProductsItem.ProductItem)
            is ProductMoreViewHolder -> holder.bind(items[position] as ProductsItem.LoadItem)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(items: List<ProductsItem>) {
        val paginationItemPosition = itemCount - 1
        notifyItemRemoved(paginationItemPosition)

        val oldItemCount = itemCount - 1
        this.items = items
        notifyItemRangeInserted(oldItemCount, itemCount - oldItemCount)
    }
}
