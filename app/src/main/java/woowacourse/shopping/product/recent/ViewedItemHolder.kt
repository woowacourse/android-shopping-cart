package woowacourse.shopping.product.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ViewedItemBinding
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.catalog.event.CatalogEventHandler

class ViewedItemHolder(
    private val binding: ViewedItemBinding,
    private val handler: CatalogEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(viewedProduct: ProductUiModel) {
        binding.product = viewedProduct
        binding.handler = handler
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            handler: CatalogEventHandler,
        ): ViewedItemHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ViewedItemBinding.inflate(inflater, parent, false)

            return ViewedItemHolder(binding, handler)
        }
    }
}
