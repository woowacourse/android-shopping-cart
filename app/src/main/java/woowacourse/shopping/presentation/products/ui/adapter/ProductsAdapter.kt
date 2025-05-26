package woowacourse.shopping.presentation.products.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.products.ProductsViewModel
import woowacourse.shopping.presentation.products.ui.ProductsItem
import woowacourse.shopping.presentation.products.ui.ProductsItemViewType
import woowacourse.shopping.presentation.products.ui.viewholder.LastWatchProductsViewHolder
import woowacourse.shopping.presentation.products.ui.viewholder.LastWatchTitleViewHolder
import woowacourse.shopping.presentation.products.ui.viewholder.LoadMoreViewHolder
import woowacourse.shopping.presentation.products.ui.viewholder.ProductViewHolder
import woowacourse.shopping.presentation.products.ui.viewholder.ProductsItemViewHolder

@SuppressLint("NotifyDataSetChanged")
class ProductsAdapter(
    private val onClickHandler: OnClickHandler,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: ProductsViewModel,
) : RecyclerView.Adapter<ProductsItemViewHolder<ProductsItem, ViewDataBinding>>() {
    private val productsItems: MutableList<ProductsItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsItemViewHolder<ProductsItem, ViewDataBinding> =
        when (ProductsItemViewType.entries[viewType]) {
            ProductsItemViewType.PRODUCT ->
                ProductViewHolder(parent, onClickHandler, lifecycleOwner, viewModel)

            ProductsItemViewType.LOAD_MORE ->
                LoadMoreViewHolder(parent, onClickHandler)

            ProductsItemViewType.LAST_WATCH ->
                LastWatchProductsViewHolder(parent, onClickHandler, lifecycleOwner, viewModel)

            ProductsItemViewType.LAST_WATCH_TITLE ->
                LastWatchTitleViewHolder(parent)
        } as ProductsItemViewHolder<ProductsItem, ViewDataBinding>

    override fun onBindViewHolder(
        holder: ProductsItemViewHolder<ProductsItem, ViewDataBinding>,
        position: Int,
    ) {
        val productsItem = productsItems[position]
        holder.bind(productsItem)
    }

    override fun getItemCount(): Int = productsItems.size

    override fun getItemViewType(position: Int): Int =
        when (productsItems[position]) {
            is ProductsItem.ProductProductsItem -> ProductsItemViewType.PRODUCT.typeId
            is ProductsItem.LoadMoreProductsItem -> ProductsItemViewType.LOAD_MORE.typeId
            is ProductsItem.LastWatchProductsItem -> ProductsItemViewType.LAST_WATCH.typeId
            is ProductsItem.LastWatchTitleItem -> ProductsItemViewType.LAST_WATCH_TITLE.typeId
        }


    fun updateProductItems(newItems: List<Product>, lastWatchedItems: List<Product>) {
        productsItems.clear()
        if (lastWatchedItems.isNotEmpty()) {
            productsItems.add(ProductsItem.LastWatchTitleItem)
            productsItems.add(ProductsItem.LastWatchProductsItem(lastWatchedItems))
        }
        productsItems.addAll(newItems.map { ProductsItem.ProductProductsItem(it) })
        notifyDataSetChanged()
    }

    fun updateLoadMoreItem(isLoadable: Boolean) {
        if (isLoadable) {
            productsItems.add(ProductsItem.LoadMoreProductsItem)
            notifyItemInserted(productsItems.lastIndex)
        }
    }

    fun refreshItemById(id: Int) {
        val index = productsItems.indexOfFirst {
            it is ProductsItem.ProductProductsItem && it.value.id == id
        }
        if (index != -1) notifyItemChanged(index)
    }

    interface OnClickHandler :
        ProductViewHolder.OnClickHandler,
        LoadMoreViewHolder.OnClickHandler
}