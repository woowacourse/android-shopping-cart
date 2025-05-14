package woowacourse.shopping.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductBinding

class ProductViewHolder(
    parent: ViewGroup,
    onClickHandler: OnClickHandler,
) : ItemViewHolder<Item.ProductItem, ItemProductBinding>(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    init {
        binding.onClickHandler = onClickHandler
    }

    override fun bind(item: Item.ProductItem) {
        super.bind(item)
        binding.product = item.value
    }

    interface OnClickHandler {
        fun onProductClick(id: Int)
    }
}
