package woowacourse.shopping.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.custom.CartCountView

class ProductViewHolder(
    parent: ViewGroup,
    private val onClickHandler: OnClickHandler,
) : ProductsItemViewHolder<ProductsItem.ProductItem, ItemProductBinding>(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    init {
        binding.onClickHandler = onClickHandler
    }

    override fun bind(item: ProductsItem.ProductItem) {
        super.bind(item)
        binding.cartProduct = item.value
        binding.productCartProductCount.setCount(item.value.quantity)
        binding.productCartProductCount.setOnClickHandler(
            object : CartCountView.OnClickHandler {
                override fun onIncreaseClick() {
                    onClickHandler.onIncreaseClick(item.value.product.id)
                }

                override fun onDecreaseClick() {
                    onClickHandler.onDecreaseClick(item.value.product.id)
                }
            },
        )
    }

    interface OnClickHandler {
        fun onProductClick(id: Int)

        fun onIncreaseClick(id: Int)

        fun onDecreaseClick(id: Int)
    }
}
