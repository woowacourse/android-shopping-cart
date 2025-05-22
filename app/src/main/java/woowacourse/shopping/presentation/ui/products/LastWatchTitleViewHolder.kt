package woowacourse.shopping.presentation.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemLastProductTitleBinding

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
