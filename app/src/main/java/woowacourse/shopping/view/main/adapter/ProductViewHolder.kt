package woowacourse.shopping.view.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.main.event.ProductsAdapterEventHandler
import woowacourse.shopping.view.util.QuantitySelectorEventHandler

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val quantitySelectorEventHandler: QuantitySelectorEventHandler,
    private val productsAdapterEventHandler: ProductsAdapterEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Cart) {
        binding.cart = item
        binding.quantitySelectorEventHandler = this.quantitySelectorEventHandler
        binding.productsAdapterEventHandler = productsAdapterEventHandler
    }
}
