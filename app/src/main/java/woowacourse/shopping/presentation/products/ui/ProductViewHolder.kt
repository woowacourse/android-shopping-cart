package woowacourse.shopping.presentation.products.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.products.ProductsViewModel

class ProductViewHolder(
    parent: ViewGroup,
    val onClickHandler: OnClickHandler,
    val lifecycleOwner: LifecycleOwner,
    val viewModel: ProductsViewModel,
) : ProductsItemViewHolder<ProductsItem.ProductProductsItem, ItemProductBinding>(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    override fun bind(item: ProductsItem.ProductProductsItem) {
        super.bind(item)
        binding.product = item.value
        binding.lifecycleOwner = lifecycleOwner
        binding.tvProductPlus.setOnClickListener { onClickHandler.onPlusClick(item.value.id) }
        binding.tvProductMinus.setOnClickListener { onClickHandler.onMinusClick(item.value.id) }
        binding.ivProductPreview.setOnClickListener { onClickHandler.onProductClick(item.value.id) }
        binding.vm = viewModel
        binding.btnProductOpenCount.setOnClickListener { onClickHandler.onPlusClick(item.value.id) }
    }

    interface OnClickHandler {
        fun onProductClick(id: Int)

        fun onInsertCartClick(id: Int)

        fun onPlusClick(id: Int)

        fun onMinusClick(id: Int)
    }
}
