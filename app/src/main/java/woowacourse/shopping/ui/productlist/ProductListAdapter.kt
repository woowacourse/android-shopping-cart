package woowacourse.shopping.ui.productlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R

class ProductListAdapter(
    private val items: MutableList<ProductListViewType>,
    private val productClickListener: ProductClickListener,
    private val loadMoreClickListener: LoadMoreClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ProductListViewType.ProductItemType -> R.layout.product_item
            is ProductListViewType.LoadMoreType -> R.layout.load_more_item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.product_item -> ProductItemViewHolder.create(parent, productClickListener)
            R.layout.load_more_item -> LoadMoreViewHolder.create(parent, loadMoreClickListener)
            else -> throw IllegalArgumentException(ERROR_INVALID_VIEW_HOLDER_TYPE)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductItemViewHolder -> holder.bind(items[position] as ProductListViewType.ProductItemType)
        }
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<ProductListViewType>) {
        if (isLoadMore(newItems)) return
        compareItems(newItems)
    }

    private fun isLoadMore(newItems: List<ProductListViewType>): Boolean {
        val positionStart = this.items.size
        val itemCount = newItems.size - this.items.size
        if (itemCount == 0) return false

        this.items.clear()
        this.items.addAll(newItems)
        notifyItemRangeInserted(positionStart, itemCount)
        return true
    }

    private fun compareItems(newItems: List<ProductListViewType>) {
        var position = 0
        while (items.isNotEmpty() && items[position] == newItems[position]) {
            position++
        }

        this.items.clear()
        this.items.addAll(newItems)
        notifyItemChanged(position)
    }

    companion object {
        private const val ERROR_INVALID_VIEW_HOLDER_TYPE = "지원하지 않는 타입입니다."
    }
}
