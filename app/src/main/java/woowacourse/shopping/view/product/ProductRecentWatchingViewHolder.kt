package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductRecentWatchingBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.product.RecentProductViewHolder.ProductRecentMoreWatchingClickListener

class ProductRecentWatchingViewHolder(
    private val binding: ItemProductRecentWatchingBinding,
    private val productListener: ProductRecentMoreWatchingClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentWatchingProducts: List<Product>) {
        val adapter = RecentProductAdapter(recentWatchingProducts, productListener)

        binding.productRecentWatching.apply {
            this.adapter = adapter
        }
    }

    companion object {
        fun of(
            parent: ViewGroup,
            productListener: ProductRecentMoreWatchingClickListener,
        ): ProductRecentWatchingViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductRecentWatchingBinding.inflate(layoutInflater, parent, false)
            return ProductRecentWatchingViewHolder(binding, productListener)
        }
    }
}
