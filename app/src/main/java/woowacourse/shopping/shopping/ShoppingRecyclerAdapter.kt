package woowacourse.shopping.shopping

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.ProductCountClickListener
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.shopping.ReadMoreViewHolder.Companion.READ_MORE_ITEM_TYPE
import woowacourse.shopping.shopping.ShoppingItemViewHolder.Companion.PRODUCT_ITEM_TYPE
import woowacourse.shopping.shopping.recentview.RecentViewedLayoutViewHolder
import woowacourse.shopping.shopping.recentview.RecentViewedLayoutViewHolder.Companion.RECENT_VIEWED_ITEM_TYPE

class ShoppingRecyclerAdapter(
    products: List<ProductUiModel>,
    recentViewedProducts: List<ProductUiModel>,
    private val onProductClicked: (ProductUiModel) -> Unit,
    private val onReadMoreClicked: () -> Unit,
    private val countClickListener: ProductCountClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val products: MutableList<ProductUiModel> =
        products.toMutableList()
    private val recentViewedProducts: MutableList<ProductUiModel> =
        recentViewedProducts.toMutableList()

    override fun getItemViewType(position: Int): Int {
        if (position + 1 == itemCount) return READ_MORE_ITEM_TYPE
        if (recentViewedProducts.isEmpty()) {
            return PRODUCT_ITEM_TYPE
        }
        return if (position == INITIAL_POSITION) RECENT_VIEWED_ITEM_TYPE else PRODUCT_ITEM_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RECENT_VIEWED_ITEM_TYPE -> RecentViewedLayoutViewHolder.from(parent)

            PRODUCT_ITEM_TYPE -> ShoppingItemViewHolder.from(parent, countClickListener)

            READ_MORE_ITEM_TYPE -> ReadMoreViewHolder.from(parent)

            else -> throw IllegalArgumentException(VIEW_TYPE_ERROR)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            RECENT_VIEWED_ITEM_TYPE ->
                (holder as RecentViewedLayoutViewHolder).bind(recentViewedProducts)

            PRODUCT_ITEM_TYPE ->

                (holder as ShoppingItemViewHolder).bind(
                    productUiModel = if (recentViewedProducts.isEmpty()) {
                        products[position]
                    } else {
                        products[position - 1]
                    },
                    onClicked = onProductClicked,
                )

            READ_MORE_ITEM_TYPE ->
                (holder as ReadMoreViewHolder).bind(onReadMoreClicked)
        }
    }

    override fun getItemCount(): Int {
        return if (recentViewedProducts.isEmpty()) products.size + 1 else products.size + 2
    }

    fun refreshRecentViewedItems(toReplace: List<ProductUiModel>) {
        recentViewedProducts.clear()
        recentViewedProducts.addAll(toReplace)
        notifyItemChanged(0)
    }

    fun refreshShoppingItems(toReplace: List<ProductUiModel>) {
        products.clear()
        products.addAll(toReplace)

        notifyItemRangeChanged(
            if (recentViewedProducts.isEmpty()) 0 else 1,
            itemCount,
        )
    }

    fun readMoreShoppingItems(toAdd: List<ProductUiModel>) {
        if (toAdd.isNotEmpty()) {
            products.addAll(toAdd)
            notifyItemInserted(products.size + 1 - toAdd.size)
        }
    }

    companion object {
        private const val VIEW_TYPE_ERROR = "해당 타입의 뷰홀더는 생성할 수 없습니다."
        private const val INITIAL_POSITION = 0
    }
}
