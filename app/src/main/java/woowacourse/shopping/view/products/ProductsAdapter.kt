package woowacourse.shopping.view.products

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R

class ProductsAdapter(
    private val productClickListener: ProductsClickListener,
    private val loadMoreClickListener: LoadMoreClickListener,
) : ListAdapter<ProductListViewType, RecyclerView.ViewHolder>(ProductDiffUtil()) {
    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is ProductListViewType.ProductType -> R.layout.item_product
            is ProductListViewType.LoadMoreType -> R.layout.item_load_more
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_product ->
                ProductViewHolder.create(
                    parent,
                    productClickListener,
                )

            R.layout.item_load_more -> LoadMoreViewHolder.create(parent, loadMoreClickListener)

            else -> throw IllegalArgumentException("지원하지 않는 타입입니다")
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> {
                val productType = getItem(position) as ProductListViewType.ProductType
                val quantity =
                    holder.bind(productType, quantity)
            }
        }
    }
}
