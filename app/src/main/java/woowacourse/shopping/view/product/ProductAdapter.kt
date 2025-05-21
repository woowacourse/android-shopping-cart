package woowacourse.shopping.view.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val productListener: ProductListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ProductsItem> = emptyList()

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (ProductsItem.ItemType.from(viewType)) {
            ProductsItem.ItemType.PRODUCT -> ProductViewHolder.of(parent, productListener)
            ProductsItem.ItemType.MORE -> ProductMoreViewHolder.of(parent, productListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(items[position] as ProductsItem.ProductItem)
        }
    }

    override fun getItemCount(): Int = items.size

    fun appendItems(items: List<ProductsItem>) {
        val previousSize = this.items.size
        val insertedCount = items.size - previousSize

        this.items = items

        notifyItemRangeInserted(previousSize, insertedCount)
    }

    interface ProductListener :
        ProductViewHolder.ProductClickListener,
        ProductMoreViewHolder.ProductMoreClickListener
}
