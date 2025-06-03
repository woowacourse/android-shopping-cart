package woowacourse.shopping.view.product.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.main.adapter.ViewItems.ViewType

class ProductAdapter(
    private val onShowMore: () -> Boolean,
    private val onAddCart: (CartItem, Int, View) -> Unit,
    private val navigateToProductDetail: (Product) -> Unit,
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {
    private var items: List<ViewItems> = emptyList()

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        when (ViewType.Companion.from(viewType)) {
            ViewType.PRODUCTS ->
                ProductViewHolder.from(
                    parent,
                    navigateToProductDetail,
                    onAddCart,
                    onIncrease,
                    onDecrease,
                )

            ViewType.SHOW_MORE -> ShowMoreViewHolder.from(parent, onShowMore)
            ViewType.RECENTLY_VIEWED_PRODUCTS ->
                RecentlyViewedProductHolder.from(
                    parent,
                    navigateToProductDetail,
                )

            ViewType.DIVIDER -> DividerHolder.from(parent)
        }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(items[position] as ViewItems.Products)
            is RecentlyViewedProductHolder -> holder.bind(items[position] as ViewItems.RecentlyViewedProducts)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(items: List<ViewItems>) {
        val previousSize = this.items.size
        val insertedCount = items.size - previousSize

        this.items = items
        notifyItemRangeInserted(previousSize, insertedCount)
    }
}
