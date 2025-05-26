package woowacourse.shopping.view.recentproduct

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.product.Product

class RecentProductsAdapter(
    private val products: MutableList<Product> = mutableListOf(),
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder = RecentProductViewHolder.from(parent)

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateRecentProductsView(updatedProducts: List<Product>) {
        val distinctList = updatedProducts.distinctBy { it.id }
        products.clear()
        products.addAll(distinctList)
        notifyItemInserted(products.lastIndex)
    }
}
