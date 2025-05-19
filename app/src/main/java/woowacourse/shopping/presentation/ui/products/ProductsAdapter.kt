package woowacourse.shopping.presentation.ui.products

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product

@SuppressLint("NotifyDataSetChanged")
class ProductsAdapter(
    private val onClickHandler: OnClickHandler,
) : RecyclerView.Adapter<ProductsItemViewHolder<ProductsItem, ViewDataBinding>>() {
    private val productsItems: MutableList<ProductsItem> = mutableListOf<ProductsItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsItemViewHolder<ProductsItem, ViewDataBinding> =
        when (ProductsItemViewType.entries[viewType]) {
            ProductsItemViewType.PRODUCT -> ProductViewHolder(parent, onClickHandler)
            ProductsItemViewType.LOAD_MORE -> LoadMoreViewHolder(parent, onClickHandler)
        } as ProductsItemViewHolder<ProductsItem, ViewDataBinding>

    override fun onBindViewHolder(
        holder: ProductsItemViewHolder<ProductsItem, ViewDataBinding>,
        position: Int,
    ) {
        val productsItem: ProductsItem = productsItems[position]
        holder.bind(productsItem)
    }

    override fun getItemCount(): Int = productsItems.size

    override fun getItemViewType(position: Int): Int = when(productsItems[position]) {
        is ProductsItem.ProductProductsItem -> 0
        is ProductsItem.LoadMoreProductsItem -> 1
    }
    fun updateProductItems(newItems: List<Product>) {
        productsItems.clear()
        productsItems.addAll(newItems.map { ProductsItem.ProductProductsItem(it) })
    }

    fun updateLoadMoreItem(isLoadable: Boolean) {
        if (isLoadable) productsItems.add(ProductsItem.LoadMoreProductsItem)
    }

    interface OnClickHandler :
        ProductViewHolder.OnClickHandler,
        LoadMoreViewHolder.OnClickHandler
}
