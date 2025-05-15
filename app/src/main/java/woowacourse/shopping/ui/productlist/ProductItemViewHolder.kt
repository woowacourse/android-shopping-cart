package woowacourse.shopping.ui.productlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.domain.product.Product

class ProductItemViewHolder(
    private val binding: ProductItemBinding,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productClickListener = productClickListener
    }

    fun bind(item: ProductListViewType.ProductItemType) {
        binding.product = item.product
    }
}
