package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.domain.product.Product

class RecentViewedProductViewHolder(
    private val binding: ItemRecentViewedProductBinding,
    onSelectProduct: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onSelectProduct = onSelectProduct
    }

    fun bind(product: Product) {
        binding.product = product
        binding.imageUrl = product.imageUrl
    }

    companion object {
        fun of(
            parent: ViewGroup,
            onSelectProduct: (Product) -> Unit,
        ): RecentViewedProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentViewedProductBinding.inflate(layoutInflater, parent, false)
            return RecentViewedProductViewHolder(binding, onSelectProduct)
        }
    }
}
