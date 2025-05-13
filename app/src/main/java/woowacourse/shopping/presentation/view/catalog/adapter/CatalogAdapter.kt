package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductUiModel

class CatalogAdapter(
    products: List<ProductUiModel> = emptyList(),
    private val eventListener: CatalogEventListener,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val products = products.toMutableList()

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder = ProductViewHolder.from(parent, eventListener)

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateProducts(products: List<ProductUiModel>) {
        val previousSize = getItemCount()
        this.products.clear()
        this.products.addAll(products)
        notifyItemRangeInserted(previousSize, products.size)
    }

    interface CatalogEventListener {
        fun onProductClicked(product: ProductUiModel)
    }
}
