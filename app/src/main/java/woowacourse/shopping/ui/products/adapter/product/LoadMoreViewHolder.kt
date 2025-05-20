package woowacourse.shopping.ui.products.adapter.product

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(
    parent: ViewGroup,
    onClickHandler: OnClickHandler,
) : ProductsItemViewHolder<ProductsItem.LoadMoreItem, ItemLoadMoreBinding>(
        ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    init {
        binding.onClickHandler = onClickHandler
    }

    fun interface OnClickHandler {
        fun onLoadMoreClick()
    }
}
