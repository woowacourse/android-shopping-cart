package woowacourse.shopping.shopping

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.shopping.recentview.RecentViewedLayoutViewHolder

class ShoppingRecyclerAdapter(
    products: List<ProductUiModel>,
    recentViewedProducts: List<ProductUiModel>,
    private val onProductClicked: (ProductUiModel) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val products: MutableList<ProductUiModel> =
        products.toMutableList()
    private var recentViewedProducts: MutableList<ProductUiModel> =
        recentViewedProducts.toMutableList()

    override fun getItemViewType(position: Int): Int {
        if (recentViewedProducts.isEmpty()) {
            return PRODUCT_ITEM_TYPE
        }
        return if (position == INITIAL_POSITION) RECENT_VIEWED_ITEM_TYPE else PRODUCT_ITEM_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RECENT_VIEWED_ITEM_TYPE -> RecentViewedLayoutViewHolder.from(parent)

            PRODUCT_ITEM_TYPE -> ShoppingItemViewHolder.from(parent)

            else -> throw IllegalArgumentException(VIEW_TYPE_ERROR)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            RECENT_VIEWED_ITEM_TYPE ->
                (holder as RecentViewedLayoutViewHolder).bind(recentViewedProducts)

            PRODUCT_ITEM_TYPE ->
                (holder as ShoppingItemViewHolder).bind(
                    productUiModel = products[position],
                    onClicked = onProductClicked,
                )
        }
    }

    override fun getItemCount(): Int = products.size

    fun refreshRecentViewedItems(toReplace: List<ProductUiModel>) {
        recentViewedProducts = toReplace.toMutableList()
        notifyItemRangeChanged(0, RECENT_VIEWED_ITEM_SIZE)
    }

    fun refreshShoppingItems(toAdd: List<ProductUiModel>) {
        products.addAll(toAdd)
        notifyItemInserted(products.size - toAdd.size)
    }

    companion object {
        private const val VIEW_TYPE_ERROR = "해당 타입의 뷰홀더는 생성할 수 없습니다."
        private const val RECENT_VIEWED_ITEM_SIZE = 10
        private const val INITIAL_POSITION = 0
        const val PRODUCT_ITEM_TYPE = 0
        const val RECENT_VIEWED_ITEM_TYPE = 1
    }
}
