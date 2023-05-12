package woowacourse.shopping.shopping

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel

class ShoppingRecyclerAdapter(
    products: List<ProductUiModel>,
    recentViewedProducts: List<ProductUiModel>,
    private val onProductClicked: (ProductUiModel) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val products: MutableList<ProductUiModel> =
        products.toMutableList()
    private val recentViewedProducts: MutableList<ProductUiModel> =
        recentViewedProducts.toMutableList()

    override fun getItemViewType(position: Int): Int {
        if (recentViewedProducts.isEmpty()) {
            return ShoppingRecyclerItemViewType.PRODUCT.ordinal
        }
        return ShoppingRecyclerItemViewType.valueOf(position).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (ShoppingRecyclerItemViewType.find(viewType)) {
            ShoppingRecyclerItemViewType.RECENT_VIEWED ->
                RecentViewedLayoutViewHolder.from(parent)

            ShoppingRecyclerItemViewType.PRODUCT ->
                ShoppingItemViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ShoppingRecyclerItemViewType.RECENT_VIEWED.ordinal ->
                (holder as RecentViewedLayoutViewHolder).bind(recentViewedProducts)

            ShoppingRecyclerItemViewType.PRODUCT.ordinal ->
                (holder as ShoppingItemViewHolder).bind(
                    productUiModel = products[position],
                    onClicked = onProductClicked
                )
        }
    }

    override fun getItemCount(): Int = products.size

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
