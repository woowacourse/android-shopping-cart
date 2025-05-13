package woowacourse.shopping.view.main

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductsViewHolder(
    private val binding: ItemProductBinding,
    handler: ProductsEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(item: Product) {
        binding.product = item
    }
}
