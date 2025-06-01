package woowacourse.shopping.product.catalog.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.catalog.event.CatalogEventHandler

class ProductViewHolder(
    private val binding: ProductItemBinding,
    private val catalogHandler: CatalogEventHandler,
    private val handler: ProductQuantityHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel) {
        binding.product = product
        binding.catalogHandler = catalogHandler
        binding.handler = handler
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            catalogHandler: CatalogEventHandler,
            handler: ProductQuantityHandler,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductItemBinding.inflate(inflater, parent, false)
            binding.handler = handler
            binding.catalogHandler = catalogHandler

            return ProductViewHolder(binding, catalogHandler, handler)
        }
    }
}
