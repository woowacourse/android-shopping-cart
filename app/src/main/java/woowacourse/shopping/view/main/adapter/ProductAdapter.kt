package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.view.core.base.BaseViewHolder
import woowacourse.shopping.view.core.handler.CartQuantityHandler
import woowacourse.shopping.view.main.adapter.recent.RecentProductViewHolder
import woowacourse.shopping.view.main.state.LoadState
import woowacourse.shopping.view.main.state.ProductUiState

class ProductAdapter(
    private val handler: ProductAdapterEventHandler,
) : ListAdapter<ProductRvItems, BaseViewHolder<ViewBinding>>(ProductAdapterDiffer()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return when (ProductRvItems.ViewType.entries[viewType]) {
            ProductRvItems.ViewType.VIEW_TYPE_PRODUCT ->
                ProductViewHolder(
                    parent,
                    handler as ProductViewHolder.Handler,
                    handler as CartQuantityHandler,
                )

            ProductRvItems.ViewType.VIEW_TYPE_LOAD ->
                LoadViewHolder(parent, handler)

            ProductRvItems.ViewType.VIEW_TYPE_RECENT_PRODUCT ->
                RecentProductViewHolder(parent, handler)

            ProductRvItems.ViewType.VIEW_TYPE_DIVIDER ->
                DividerViewHolder(parent)
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding>,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is ProductRvItems.ProductItem -> (holder as ProductViewHolder).bind(item)
            is ProductRvItems.LoadItem -> (holder as LoadViewHolder).bind(item)
            is ProductRvItems.RecentProductItem -> (holder as RecentProductViewHolder).bind(item)
            ProductRvItems.DividerItem -> Unit
        }
    }

    fun submitList(newUiState: ProductUiState) {
        val newItems = mutableListOf<ProductRvItems>()

        if (newUiState.historyItems.isNotEmpty()) {
            newItems += ProductRvItems.RecentProductItem(newUiState.historyItems)
        }

        newItems += newUiState.productItems.map { ProductRvItems.ProductItem(it) }

        if (newUiState.load is LoadState.CanLoad) {
            newItems += ProductRvItems.LoadItem
        }

        this.submitList(newItems)
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    interface Handler :
        LoadViewHolder.Handler,
        ProductViewHolder.Handler,
        HistoryViewHolder.Handler
}
