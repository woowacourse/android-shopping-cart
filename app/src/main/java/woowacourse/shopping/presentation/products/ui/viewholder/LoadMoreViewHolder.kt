package woowacourse.shopping.presentation.products.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.presentation.products.ui.ProductsItem

class LoadMoreViewHolder(
    parent: ViewGroup,
    onClickHandler: OnClickHandler,
) : ProductsItemViewHolder<ProductsItem.LoadMoreProductsItem, ItemLoadMoreBinding>(
        ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    init {
        binding.onClickHandler = onClickHandler
    }

    interface OnClickHandler {
        fun onLoadMoreClick()
    }
}
