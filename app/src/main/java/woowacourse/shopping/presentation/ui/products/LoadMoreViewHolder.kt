package woowacourse.shopping.presentation.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemLoadMoreBinding

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
