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
) : RecyclerView.Adapter<ProductsViewHolder>() {
    private var productUiModels: List<ProductUiModel> = listOf()
    var quantityInfo = QuantityInfo<ProductUiModel>()
        private set

    var currentPage: Int = 0
        private set

    override fun getItemCount(): Int = productUiModels.size

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        val item = productUiModels[position]
        val quantityLiveData = quantityInfo[item]
        holder.bind(item, quantityLiveData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsViewHolder {
        return ProductsViewHolder(parent, handler)
    }

    fun updateProducts(mainRecyclerViewProduct: MainRecyclerViewProduct) {
        val newProducts = mainRecyclerViewProduct.page.items
        val newShoppingCartItems = mainRecyclerViewProduct.shoppingCartItemUiModels

        currentPage = mainRecyclerViewProduct.page.currentPage
        productUiModels += newProducts
        quantityInfo = quantityInfo + QuantityInfo(newProducts.quantityMap(newShoppingCartItems))
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
    }
}
