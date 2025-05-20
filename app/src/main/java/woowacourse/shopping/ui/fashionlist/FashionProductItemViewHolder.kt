package woowacourse.shopping.ui.fashionlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding

class FashionProductItemViewHolder(
    private val binding: ProductItemBinding,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productClickListener = productClickListener
    }

    fun bind(item: ProductListViewType.FashionProductItemType) {
        binding.product = item.product
    }
}
