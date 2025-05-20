package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.core.base.BaseViewHolder
import woowacourse.shopping.view.main.vm.LoadState
import woowacourse.shopping.view.main.vm.ProductUiState

class ProductAdapter(
    items: List<ProductRvItems>,
    private val handler: ProductsAdapterEventHandler,
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {
    private val items: MutableList<ProductRvItems> = items.toMutableList()
    private val productCount get() = items.count { it is ProductRvItems.ProductItem }

    fun submitList(newItems: ProductUiState) {
        removeLoadItem()
        addProductItems(newItems.items)
        generateLoad(newItems.load)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return when (ProductRvItems.ViewType.entries[viewType]) {
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

    private fun addProductItems(newItems: List<Product>) {
        val subsList = subtractItems(newItems)

        items.addAll(subsList)
        notifyItemRangeInserted(items.size - subsList.size, subsList.size)
    }

    private fun subtractItems(items: List<Product>): List<ProductRvItems.ProductItem> {
        return items
            .drop(productCount)
            .map(ProductRvItems::ProductItem)
    }

    private fun generateLoad(load: LoadState) {
        if (load is LoadState.CanLoad) addLoadItem()
    }

    private fun addLoadItem() {
        items.add(ProductRvItems.LoadItem)
        notifyItemInserted(items.size - 1)
    }

    private fun removeLoadItem() {
        val index = items.indexOfFirst { it is ProductRvItems.LoadItem }
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
