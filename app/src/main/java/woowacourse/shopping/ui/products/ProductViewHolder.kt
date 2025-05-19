package woowacourse.shopping.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductBinding

class ProductViewHolder(
    parent: ViewGroup,
    onClickHandler: OnClickHandler,
) : ProductsItemViewHolder<ProductsItem.ProductItem, ItemProductBinding>(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    init {
        binding.onClickHandler = onClickHandler
    }

    override fun bind(item: ProductsItem.ProductItem) {
        super.bind(item)
        binding.cartProduct = item.value
    }

    fun interface OnClickHandler {
        fun onProductClick(id: Int)
    }
}
