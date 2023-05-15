package woowacourse.shopping.shopping

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel
import woowacourse.shopping.shopping.ShoppingRecyclerItem.ReadMoreDescription
import woowacourse.shopping.shopping.ShoppingRecyclerItem.RecentViewedProducts
import woowacourse.shopping.shopping.ShoppingRecyclerItem.ShoppingProduct
import woowacourse.shopping.shopping.ShoppingRecyclerItemViewType.PRODUCT
import woowacourse.shopping.shopping.ShoppingRecyclerItemViewType.READ_MORE
import woowacourse.shopping.shopping.ShoppingRecyclerItemViewType.RECENT_VIEWED

class ShoppingRecyclerAdapter(
    products: List<ProductUiModel>,
    private var recentViewedProducts: List<RecentViewedProductUiModel>,
    private val onProductClicked: (ProductUiModel) -> Unit,
    private val onReadMoreButtonClicked: () -> Unit,
    private val readMoreButtonDescription: String,
) : RecyclerView.Adapter<ShoppingRecyclerItemViewHolder<out ShoppingRecyclerItem, out ViewDataBinding>>() {

    private val products: MutableList<ProductUiModel> =
        products.toMutableList()

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && recentViewedProducts.isEmpty()) {
            return PRODUCT.ordinal
        }
        return ShoppingRecyclerItemViewType.valueOf(
            position = position,
            shoppingItemsSize = products.size
        ).ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingRecyclerItemViewHolder<out ShoppingRecyclerItem, out ViewDataBinding> {

        return when (ShoppingRecyclerItemViewType.find(viewType)) {
            RECENT_VIEWED -> RecentViewedViewHolder.from(parent)

            PRODUCT ->
                ShoppingProductViewHolder
                    .from(parent)
                    .apply {
                        setOnClicked(onClicked = onProductClicked)
                    }

            READ_MORE ->
                ReadMoreViewHolder
                    .from(parent)
                    .apply {
                        setOnClicked(onClicked = onReadMoreButtonClicked)
                    }
        }
    }

    override fun onBindViewHolder(
        holder: ShoppingRecyclerItemViewHolder<out ShoppingRecyclerItem, out ViewDataBinding>,
        position: Int,
    ) {
        when (holder) {
            is RecentViewedViewHolder -> holder.bind(
                itemData = RecentViewedProducts(recentViewedProducts)
            )

            is ShoppingProductViewHolder -> holder.bind(
                itemData = ShoppingProduct(products[position]),
            )

            is ReadMoreViewHolder -> holder.bind(
                itemData = ReadMoreDescription(readMoreButtonDescription)
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
