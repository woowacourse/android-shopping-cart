package woowacourse.shopping.shopping

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel

class ShoppingRecyclerAdapter(
    products: List<ProductUiModel>,
    recentViewedProducts: List<ProductUiModel>,
    private val onProductClicked: (ProductUiModel) -> Unit,
    private val onShowMoreButtonClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val products: MutableList<ProductUiModel> =
        products.toMutableList()
    private val recentViewedProducts: MutableList<ProductUiModel> =
        recentViewedProducts.toMutableList()

    override fun getItemViewType(position: Int): Int {
        if (recentViewedProducts.isEmpty()) {
            return ShoppingRecyclerItemViewType.PRODUCT.ordinal
        }
        return ShoppingRecyclerItemViewType.valueOf(
            position,
            products.size
        ).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (ShoppingRecyclerItemViewType.find(viewType)) {
            ShoppingRecyclerItemViewType.RECENT_VIEWED ->
                RecentViewedLayoutViewHolder.from(parent)

            ShoppingRecyclerItemViewType.PRODUCT ->
                ShoppingItemViewHolder.from(parent)

            ShoppingRecyclerItemViewType.READ_MORE ->
                ReadMoreItemViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ShoppingRecyclerItemViewType.RECENT_VIEWED.ordinal ->
                (holder as RecentViewedLayoutViewHolder).bind(
                    recentViewedProducts = recentViewedProducts
                )

            ShoppingRecyclerItemViewType.PRODUCT.ordinal ->
                (holder as ShoppingItemViewHolder).bind(
                    productUiModel = products[position],
                    onClicked = onProductClicked
                )

            ShoppingRecyclerItemViewType.READ_MORE.ordinal ->
                (holder as ReadMoreItemViewHolder).bind(
                    onClicked = onShowMoreButtonClicked
                )
        }
    }

    override fun getItemCount(): Int = products.size + 1

    @SuppressLint("NotifyDataSetChanged")
    fun refreshRecentViewedItems(toRemove: ProductUiModel?, toAdd: ProductUiModel) {
        recentViewedProducts.add(0, toAdd)
        toRemove?.let {
            recentViewedProducts.remove(it)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshShoppingItems(toAdd: List<ProductUiModel>) {
        products.addAll(toAdd)
        notifyDataSetChanged()
    }
}
