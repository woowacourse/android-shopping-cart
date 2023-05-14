package woowacourse.shopping.shopping

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel

class ShoppingRecyclerAdapter(
    products: List<ProductUiModel>,
    private var recentViewedProducts: List<RecentViewedProductUiModel>,
    private val onProductClicked: (ProductUiModel) -> Unit,
    private val onReadMoreButtonClicked: () -> Unit
) : RecyclerView.Adapter<ShoppingRecyclerItemViewHolder>() {

    private val products: MutableList<ProductUiModel> =
        products.toMutableList()

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && recentViewedProducts.isEmpty()) {
            return ShoppingRecyclerItemViewType.PRODUCT.ordinal
        }
        return ShoppingRecyclerItemViewType.valueOf(
            position = position,
            shoppingItemsSize = products.size
        ).ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingRecyclerItemViewHolder {

        return ShoppingRecyclerItemViewHolder.from(
            parent = parent,
            viewType = ShoppingRecyclerItemViewType.find(viewType)
        )
    }

    override fun onBindViewHolder(holder: ShoppingRecyclerItemViewHolder, position: Int) {
        when (holder) {
            is ShoppingRecyclerItemViewHolder.RecentViewedViewHolder ->
                holder.bind(
                    recentViewedProducts = recentViewedProducts
                )

            is ShoppingRecyclerItemViewHolder.ProductViewHolder ->
                holder.bind(
                    productUiModel = products[position],
                    onClicked = onProductClicked
                )

            is ShoppingRecyclerItemViewHolder.ReadMoreViewHolder ->
                holder.bind(
                    onClicked = onReadMoreButtonClicked
                )
        }
    }

    override fun getItemCount(): Int = products.size + 1

    @SuppressLint("NotifyDataSetChanged")
    fun refreshRecentViewedItems(products: List<RecentViewedProductUiModel>) {
        recentViewedProducts = products
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshShoppingItems(toAdd: List<ProductUiModel>) {
        products.addAll(toAdd)
        notifyDataSetChanged()
    }
}
