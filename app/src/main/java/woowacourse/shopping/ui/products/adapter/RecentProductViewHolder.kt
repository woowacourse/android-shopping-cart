package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.Product

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    private val itemClickListener: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.itemLayout.setOnClickListener {
            itemClickListener(product.id)
        }
    }
}
