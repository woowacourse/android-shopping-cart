package woowacourse.shopping.presentation.ui.products

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.viewmodel.products.ProductsViewModel

@SuppressLint("NotifyDataSetChanged")
class ProductsAdapter(
    private val onClickHandler: OnClickHandler,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: ProductsViewModel,
) : RecyclerView.Adapter<ProductsItemViewHolder<ProductsItem, ViewDataBinding>>() {
    private val productsItems: MutableList<ProductsItem> = mutableListOf<ProductsItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsItemViewHolder<ProductsItem, ViewDataBinding> =
        when (ProductsItemViewType.entries[viewType]) {
            ProductsItemViewType.PRODUCT ->
                ProductViewHolder(
                    parent,
                    onClickHandler,
                    lifecycleOwner,
                    viewModel,
                )

            ProductsItemViewType.LOAD_MORE -> LoadMoreViewHolder(parent, onClickHandler)
            ProductsItemViewType.LAST_WATCH ->
                LastWatchProductsViewHolder(
                    parent,
                    onClickHandler,
                    lifecycleOwner,
                    viewModel,
                )

            ProductsItemViewType.LAST_WATCH_TITLE ->
                LastWatchTitleViewHolder(
                    parent,
                )
        } as ProductsItemViewHolder<ProductsItem, ViewDataBinding>

    override fun onBindViewHolder(
        holder: ProductsItemViewHolder<ProductsItem, ViewDataBinding>,
        position: Int,
    ) {
        val productsItem: ProductsItem = productsItems[position]
        holder.bind(productsItem)
    }

    override fun getItemCount(): Int = productsItems.size

    override fun getItemViewType(position: Int): Int =
        when (productsItems[position]) {
            is ProductsItem.LastWatchProductsItem -> 2
            is ProductsItem.ProductProductsItem -> 0
            is ProductsItem.LoadMoreProductsItem -> 1
            is ProductsItem.LastWatchTitleItem -> 3
        }

    fun updateProductItems(newItems: List<Product>) {
        productsItems.clear()
        productsItems.add(ProductsItem.LastWatchTitleItem)
        productsItems.add(ProductsItem.LastWatchProductsItem(dummyProducts.drop(5)))
        productsItems.addAll(newItems.map { ProductsItem.ProductProductsItem(it) })
    }

    fun updateLoadMoreItem(isLoadable: Boolean) {
        if (isLoadable) productsItems.add(ProductsItem.LoadMoreProductsItem)
    }

    fun refreshItemById(id: Int) {
        val pos =
            productsItems.indexOfFirst {
                it is ProductsItem.ProductProductsItem && it.value.id == id
            }
        if (pos != -1) {
            notifyItemChanged(pos)
        }
    }

    interface OnClickHandler :
        ProductViewHolder.OnClickHandler,
        LoadMoreViewHolder.OnClickHandler
}
