package woowacourse.shopping.view.products.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.products.ProductListActionHandler

class RecentProductAdapter(val productListActionHandler: ProductListActionHandler) : RecyclerView.Adapter<RecentProductViewHolder>() {
    private val recentProducts: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        return RecentProductViewHolder(
            ItemRecentProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(recentProducts[position], productListActionHandler)
    }

    override fun getItemCount(): Int {
        return recentProducts.size
    }

    fun updateRecentProducts(newRecentProducts: List<Product>) {
        val before = recentProducts.size
        if (before == 0) {
            recentProducts.addAll(newRecentProducts)
            notifyItemInserted(newRecentProducts.size)
        } else {
            val updated = newRecentProducts.drop(before - 1)
            recentProducts.addAll(updated)
            notifyItemRangeChanged(before, newRecentProducts.size)
        }
    }
}
