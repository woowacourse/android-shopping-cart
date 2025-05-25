package woowacourse.shopping.view.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val productListener: ProductListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<ProductsItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (ProductsItem.ItemType.from(viewType)) {
            ProductsItem.ItemType.PRODUCT -> ProductViewHolder.of(parent, productListener)
            ProductsItem.ItemType.MORE -> ProductMoreViewHolder.of(parent, productListener)
            ProductsItem.ItemType.RECENT_WATCHING ->
                ProductRecentWatchingViewHolder.of(
                    parent,
                    productListener,
                )
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(items[position] as ProductsItem.ProductItem)
            is ProductRecentWatchingViewHolder -> holder.bind((items[position] as ProductsItem.RecentWatchingItem).products)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(items: List<ProductsItem>) {
        if (items.size > this.items.size) {
            appendItems(items)
            return
        }

        changeItems(items)
    }

    private fun changeItems(items: List<ProductsItem>) {
        items.forEachIndexed { index, newItem ->
            val oldItem = this.items[index]
            if (oldItem != newItem) {
                this.items[index] = newItem
                notifyItemChanged(index)
            }
        }
    }

    private fun appendItems(items: List<ProductsItem>) {
        val previousSize = this.items.size
        val insertedCount = items.size - previousSize

        this.items.clear()
        this.items.addAll(items)

        notifyItemRangeInserted(previousSize, insertedCount)
    }

    interface ProductListener :
        ProductViewHolder.ProductClickListener,
        ProductMoreViewHolder.ProductMoreClickListener,
        RecentProductViewHolder.ProductRecentMoreWatchingClickListener
}
