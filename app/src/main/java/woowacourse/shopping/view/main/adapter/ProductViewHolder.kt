package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.view.core.base.BaseViewHolder

class ProductViewHolder(
    parent: ViewGroup,
    private val handler: ProductsAdapterEventHandler,
) : BaseViewHolder<ItemProductBinding>(
        binding =
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
    ) {
    fun bind(item: ProductRvItems.ProductItem) {
        with(binding) {
            model = item
            eventHandler = handler
        }
    }
}
