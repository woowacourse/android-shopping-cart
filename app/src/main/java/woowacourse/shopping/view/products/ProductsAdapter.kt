package woowacourse.shopping.view.products

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.product.Product

class ProductsAdapter(
    private val products: MutableList<Product> = mutableListOf(),
    private val quantities: MutableMap<Long, Int> = mutableMapOf(),
    private val productClickListener: (Product) -> Unit,
    private val openQuantitySelectListener: () -> Boolean,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val openedQuantitySelectors = mutableSetOf<Long>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder =
        ProductViewHolder.from(
            parent,
            productClickListener,
            openQuantitySelectListener,
            quantitySelectButtonListener,
        )

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        val product = products[position]
        val quantity = quantities[product.id] ?: 1
        val isQuantitySelectorOpened = openedQuantitySelectors.contains(product.id)
        holder.bind(products[position], quantity, isQuantitySelectorOpened)
    }

    fun updateProductsView(list: List<Product>) {
        products.clear()
        products.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    fun updateQuantityView(
        productId: Long,
        quantity: Int,
    ) {
        quantities[productId] = quantity
        val position = products.indexOfFirst { it.id == productId }
        openedQuantitySelectors.add(productId)
        notifyItemChanged(position)
    }
}
