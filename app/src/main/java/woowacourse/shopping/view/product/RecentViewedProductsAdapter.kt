package woowacourse.shopping.view.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.product.Product

class RecentViewedProductsAdapter(
    private val onSelectProduct: (Product) -> Unit,
) : RecyclerView.Adapter<RecentViewedProductViewHolder>() {
    private var items: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentViewedProductViewHolder = RecentViewedProductViewHolder.of(parent, onSelectProduct)

    override fun onBindViewHolder(
        holder: RecentViewedProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(products: List<Product>) {
        items = products
        notifyDataSetChanged()
    }
}
