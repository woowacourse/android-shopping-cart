package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.view.uimodel.QuantityInfo
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

class ProductsAdapter(
    private val handler: ProductEventHandler,
    private val recentProductsAdapter: RecentProductsAdapter,
) : RecyclerView.Adapter<MainViewHolder>() {
    private var productUiModels: List<ProductUiModel> = listOf()
    var quantityInfo = QuantityInfo<ProductUiModel>()
        private set

    var currentPage: Int = UNLOADED_PAGE
        private set

    override fun getItemCount(): Int = productUiModels.size + 1

    override fun onBindViewHolder(
        holder: MainViewHolder,
        position: Int,
    ) {
        if (position == 0) return
        val item = productUiModels[position - 1]
        val quantityLiveData = quantityInfo[item]
        when (holder) {
            is MainViewHolder.ProductsViewHolder -> holder.bind(item, quantityLiveData)
            is MainViewHolder.RecentProductsViewHolder -> Unit
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MainViewHolder {
        return if (viewType == ViewType.RECENT_PRODUCTS.ordinal) {
            MainViewHolder.RecentProductsViewHolder(recentProductsAdapter, parent)
        } else {
            MainViewHolder.ProductsViewHolder(parent, handler)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ViewType.RECENT_PRODUCTS.ordinal
        } else {
            ViewType.PRODUCTS.ordinal
        }
    }

    fun updateProducts(mainRecyclerViewProduct: MainRecyclerViewProduct) {
        val newProducts = mainRecyclerViewProduct.page.items
        val newShoppingCartItems = mainRecyclerViewProduct.shoppingCartItemUiModels

        currentPage = mainRecyclerViewProduct.page.currentPage
        productUiModels += newProducts
        quantityInfo = mainRecyclerViewProduct.quantityInfo

        notifyItemInserted(itemCount)
    }

    fun clear() {
        val previousItemCount = itemCount
        productUiModels = listOf()
        quantityInfo.clear()
        notifyItemRangeRemoved(0, previousItemCount)
    }

    private fun List<ProductUiModel>.quantityMap(
        newShoppingCartItemUiModels: List<ShoppingCartItemUiModel>,
    ): Map<ProductUiModel, MutableLiveData<Int>> {
        return associateWith { product ->
            MutableLiveData(
                newShoppingCartItemUiModels.find { it.productUiModel.id == product.id }
                    ?.quantity ?: DEFAULT_QUANTITY,
            )
        }
    }

    companion object {
        private const val DEFAULT_QUANTITY = 0
        private const val UNLOADED_PAGE = -1
    }

    private enum class ViewType {
        RECENT_PRODUCTS,
        PRODUCTS,
    }
}
