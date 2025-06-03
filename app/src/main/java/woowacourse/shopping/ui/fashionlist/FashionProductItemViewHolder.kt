package woowacourse.shopping.ui.fashionlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding

class FashionProductItemViewHolder(
    private val binding: ProductItemBinding,
    viewModel: ProductListViewModel,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.viewModel = viewModel
        binding.productClickListener = productClickListener
    }

    fun bind(fashionProductItem: ProductListViewType.FashionProductItem) {
        binding.fashionProductItem = fashionProductItem
        binding.cartItem = fashionProductItem.cartItem
        binding.executePendingBindings()
    }
}
