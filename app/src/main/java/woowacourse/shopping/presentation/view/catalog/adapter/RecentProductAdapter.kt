package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductUiModel

class RecentProductAdapter(
    products: List<ProductUiModel>,
    private val eventListener: CatalogAdapter.CatalogEventListener,
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    private val products = products.toMutableList()

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder = RecentProductViewHolder.from(parent, eventListener)

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun submitList(newItems: List<ProductUiModel>) {
        newItems.forEachIndexed { index, newProduct ->
            val oldProduct = products.getOrNull(index)

            if (oldProduct == null) {
                products.add(index, newProduct)
                notifyItemInserted(index)
                return@forEachIndexed
            }

            if (oldProduct != newProduct) {
                products[index] = newProduct
                notifyItemChanged(index)
            }
        }
    }
}
