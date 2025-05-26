package woowacourse.shopping.view.recentproduct

import android.util.Log
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

    fun updateRecentProductsView(product: Product) {
        products.add(product)
        Log.d("RecentProductsAdapter", "추가됨: $product")
        notifyItemInserted(products.lastIndex)
    }
}
