package woowacourse.shopping.view.products

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.product = item
    }
}
