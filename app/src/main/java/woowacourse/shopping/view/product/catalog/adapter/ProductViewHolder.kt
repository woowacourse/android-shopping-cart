package woowacourse.shopping.view.product.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.view.util.QuantityControlEventHandler

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val eventHandler: EventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: ProductCatalogItem.ProductItem

    init {
        binding.handler = eventHandler
        binding.itemQuantityControl.tvIncrease.setOnClickListener {
            eventHandler.onQuantityIncreaseClick(currentItem)
        }
        binding.itemQuantityControl.tvDecrease.setOnClickListener {
            eventHandler.onQuantityDecreaseClick(currentItem)
        }
    }

    fun bind(productItem: ProductCatalogItem.ProductItem) {
        currentItem = productItem
        binding.productItem = productItem
    }

    interface EventHandler : QuantityControlEventHandler<ProductCatalogItem.ProductItem> {
        fun onProductClick(item: ProductCatalogItem.ProductItem)

        fun onAddClick(item: ProductCatalogItem.ProductItem)
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
