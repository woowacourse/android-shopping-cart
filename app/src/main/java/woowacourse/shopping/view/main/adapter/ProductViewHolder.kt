package woowacourse.shopping.view.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.main.event.ProductsAdapterEventHandler

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val handler: ProductsAdapterEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Cart) {
        with(binding) {
            model = item.product
            eventHandler = handler
        }
    }
}
