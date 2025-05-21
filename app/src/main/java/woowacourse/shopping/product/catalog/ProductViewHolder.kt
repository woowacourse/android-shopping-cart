package woowacourse.shopping.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding

class ProductViewHolder(
    private val binding: ProductItemBinding,
    private val handler: CatalogEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel) {
        binding.product = product
        binding.handler = handler
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            handler: CatalogEventHandler,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductItemBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding, handler)
        }
    }
}
