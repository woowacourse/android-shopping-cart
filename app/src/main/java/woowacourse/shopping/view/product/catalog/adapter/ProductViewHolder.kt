package woowacourse.shopping.view.product.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.common.QuantityControlEventHandler

class ProductViewHolder(
    private val binding: ItemProductBinding,
    eventHandler: EventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = eventHandler
    }

    fun bind(productItem: ProductCatalogItem.ProductItem) {
        binding.product = productItem.product
        binding.quantity = productItem.quantity
    }

    interface EventHandler : QuantityControlEventHandler {
        fun onProductClick(item: Product)

        fun onAddClick(item: Product)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventHandler: EventHandler,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding, eventHandler)
        }
    }
}
