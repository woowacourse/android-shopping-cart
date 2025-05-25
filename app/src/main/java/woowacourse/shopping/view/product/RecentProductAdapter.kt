package woowacourse.shopping.view.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.product.RecentProductViewHolder.ProductRecentMoreWatchingClickListener

class RecentProductAdapter(
    private val products: List<Product>,
    private val productListener: ProductRecentMoreWatchingClickListener,
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder = RecentProductViewHolder.of(parent, productListener)

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }
}
