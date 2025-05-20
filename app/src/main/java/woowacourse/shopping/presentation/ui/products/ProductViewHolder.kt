package woowacourse.shopping.presentation.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductBinding

class ProductViewHolder(
    parent: ViewGroup,
    onClickHandler: OnClickHandler,
) : ProductsItemViewHolder<ProductsItem.ProductProductsItem, ItemProductBinding>(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    init {
        binding.onClickHandler = onClickHandler
    }

    override fun bind(item: ProductsItem.ProductProductsItem) {
        super.bind(item)
        binding.product = item.value
    }

    interface OnClickHandler {
        fun onProductClick(id: Int)
    }
}
