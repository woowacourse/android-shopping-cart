package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.core.base.BaseViewHolder

class ProductAdapter(
    items: List<ProductRvItems>,
    private val handler: ProductsAdapterEventHandler,
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {
    private val items: MutableList<ProductRvItems> = items.toMutableList()

    fun addProductItems(newItems: List<Product>) {
        val lastPosition = items.size
        items.clear()
        items.addAll(newItems.map(ProductRvItems::ProductItem))

        notifyItemRangeInserted(lastPosition, newItems.size)
    }

    fun addLoadItem() {
        items.add(ProductRvItems.LoadItem)
        notifyItemInserted(items.size - 1)
    }

    fun removeLoadItem() {
        val index = items.indexOfFirst { it is ProductRvItems.LoadItem }
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return (
            when (ProductRvItems.ViewType.entries[viewType]) {
                ProductRvItems.ViewType.VIEW_TYPE_PRODUCT ->
                    ProductViewHolder(
                        parent,
                        handler,
                    )

                ProductRvItems.ViewType.VIEW_TYPE_LOAD ->
                    LoadViewHolder(
                        parent,
                        handler,
                    )
            }
        ) as BaseViewHolder<ViewBinding>
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding>,
        position: Int,
    ) {
        when (val item = items[position]) {
            is ProductRvItems.ProductItem -> (holder as ProductViewHolder).bind(item)
            is ProductRvItems.LoadItem -> (holder as LoadViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal
}
