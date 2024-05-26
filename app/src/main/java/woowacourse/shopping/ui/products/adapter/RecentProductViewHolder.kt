package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.products.ProductItemClickListener

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    private val productItemClickListener: ProductItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.productItemClickListener = productItemClickListener
    }
}
