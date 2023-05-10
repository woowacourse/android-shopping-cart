package woowacourse.shopping.shopping

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel

class ShoppingRecyclerAdapter(
    private val products: List<ProductUiModel>,
    recentViewedProducts: List<ProductUiModel>,
    private val onProductClicked: (ProductUiModel) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val recentViewedProducts = recentViewedProducts.toMutableList()

    override fun getItemViewType(position: Int): Int {
        return ShoppingRecyclerItemViewType.valueOf(position).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ShoppingRecyclerItemViewType.RECENT_VIEWED.ordinal ->
                RecentViewedLayoutViewHolder.from(parent)

            ShoppingRecyclerItemViewType.PRODUCT.ordinal ->
                ShoppingItemViewHolder.from(parent)

            else -> throw IllegalArgumentException(VIEW_TYPE_ERROR)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ShoppingRecyclerItemViewType.RECENT_VIEWED.ordinal ->
                (holder as RecentViewedLayoutViewHolder).bind(recentViewedProducts)

            ShoppingRecyclerItemViewType.PRODUCT.ordinal ->
                (holder as ShoppingItemViewHolder).bind(
                    productUiModel = products[position],
                    onClicked = ::onProductItemClicked
                )
        }
    }

    override fun getItemCount(): Int = products.size

    @SuppressLint("NotifyDataSetChanged")
    private fun onProductItemClicked(product: ProductUiModel) {
        onProductClicked(product)
        recentViewedProducts.add(product)
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_ERROR = "해당 타입의 뷰홀더는 생성할 수 없습니다."
    }
}
