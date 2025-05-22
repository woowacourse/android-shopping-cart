package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct
import woowacourse.shopping.view.uimodel.QuantityInfo

class ProductsAdapter(
    private val handler: ProductEventHandler,
) : RecyclerView.Adapter<ProductsViewHolder>() {
    private var products: List<Product> = listOf()
    var quantityInfo = QuantityInfo<Product>()
        private set

    var currentPage: Int = 0
        private set

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        val item = products[position]
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
        val newShoppingCartItems = mainRecyclerViewProduct.shoppingCartItems

        currentPage = mainRecyclerViewProduct.page.currentPage
        products += newProducts
        quantityInfo = quantityInfo + QuantityInfo(newProducts.quantityMap(newShoppingCartItems))
        notifyItemInserted(itemCount)
    }

    fun clear() {
        val previousItemCount = itemCount
        products = listOf()
        quantityInfo.clear()
        notifyItemRangeRemoved(0, previousItemCount)
    }

    private fun List<Product>.quantityMap(newShoppingCartItems: List<ShoppingCartItem>): Map<Product, MutableLiveData<Int>> {
        return associateWith { product ->
            MutableLiveData(
                newShoppingCartItems.find { it.product.id == product.id }
                    ?.quantity ?: DEFAULT_QUANTITY,
            )
        }
    }

    companion object {
        private const val DEFAULT_QUANTITY = 0
    }
}
