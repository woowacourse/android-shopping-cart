package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct

class ProductsAdapter(
    private val totalShoppingCartSize: MutableLiveData<Int>,
) : RecyclerView.Adapter<ProductsViewHolder>() {
    private var products: List<Product> = listOf()
    private var quantity: Map<Product, MutableLiveData<Int>> = mapOf()

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        val item = products[position]
        val quantityLiveData = quantity[item] ?: return
        holder.bind(item, quantityLiveData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsViewHolder {
        return ProductsViewHolder(parent, totalShoppingCartSize)
    }

    fun getTotalQuantity(): Int {
        return quantity.values.sumOf { it.value ?: DEFAULT_QUANTITY }
    }

    fun updateProducts(mainRecyclerViewProduct: MainRecyclerViewProduct) {
        val newProducts = mainRecyclerViewProduct.page.items
        val newShoppingCartItems = mainRecyclerViewProduct.shoppingCartItems.items

        val quantityMap =
            newProducts.associateWith { product ->
                MutableLiveData(
                    newShoppingCartItems.find { it.product == product }
                        ?.quantity ?: DEFAULT_QUANTITY,
                )
            }

        products += newProducts
        quantity += quantityMap
        notifyItemInserted(itemCount)
    }

    fun observeAll(
        lifecycleOwner: LifecycleOwner,
        action: (Int) -> Unit,
    ) {
        quantity.values.forEach { liveData ->
            liveData.observe(lifecycleOwner) {
                action(it)
            }
        }
    }

    companion object {
        private const val DEFAULT_QUANTITY = 0
    }
}
