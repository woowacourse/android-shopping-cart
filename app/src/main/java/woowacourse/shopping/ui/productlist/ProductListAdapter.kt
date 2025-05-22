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

    fun update(items: List<ProductListViewType>) {
        val positionStart = this.items.size
        val itemCount = items.size - (this.items.size - LOAD_MORE_BUTTON_OFFSET)
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeInserted(positionStart, itemCount)
    }

    companion object {
        private const val ERROR_INVALID_VIEW_HOLDER_TYPE = "지원하지 않는 타입입니다."
        private const val LOAD_MORE_BUTTON_OFFSET = 1
    }
}
