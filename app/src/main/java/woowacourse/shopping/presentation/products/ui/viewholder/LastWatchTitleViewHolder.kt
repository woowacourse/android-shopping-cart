package woowacourse.shopping.presentation.products.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemLastProductTitleBinding
import woowacourse.shopping.presentation.products.ui.ProductsItem

class LastWatchTitleViewHolder(
    parent: ViewGroup,
) : ProductsItemViewHolder<ProductsItem.LastWatchTitleItem, ItemLastProductTitleBinding>(
        ItemLastProductTitleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) {
    override fun bind(item: ProductsItem.LastWatchTitleItem) {
        super.bind(item)
    }
}
