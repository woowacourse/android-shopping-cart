package woowacourse.shopping.view.products

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.product.Product

class ProductsAdapter(
    private val products: MutableList<Product> = mutableListOf(),
    private val productClickListener: (Product) -> Unit,
    private val quantitySelectListener: () -> Boolean,
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder = ProductViewHolder.from(parent, productClickListener, quantitySelectListener)

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateProductsView(list: List<Product>) {
        products.clear()
        products.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
