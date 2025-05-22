package woowacourse.shopping.view.products

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R

class ProductsAdapter(
    private val productClickListener: ProductsClickListener,
    private val loadMoreClickListener: LoadMoreClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var product: List<ProductListViewType> = emptyList()

    override fun getItemViewType(position: Int): Int =
        when (product[position]) {
            is ProductListViewType.ProductType -> R.layout.item_product
            is ProductListViewType.LoadMoreType -> R.layout.item_load_more
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_product -> ProductViewHolder.create(parent, productClickListener)
            R.layout.item_load_more -> LoadMoreViewHolder.create(parent, loadMoreClickListener)

            else -> throw IllegalArgumentException("지원하지 않는 타입입니다")
        }

    override fun getItemCount(): Int = product.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(product[position] as ProductListViewType.ProductType)
        }
    }

    fun updateProductsView(list: List<ProductListViewType>?) {
        Log.d("TAG", "updateProductsView: $list")
        product = list.orEmpty()
        notifyDataSetChanged()
    }
}
