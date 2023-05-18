package woowacourse.shopping.feature.main.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMainProductBinding
import woowacourse.shopping.model.ProductUiModel

class MainProductViewHolder(
    private val binding: ItemMainProductBinding,
    private val mainProductClickListener: MainProductClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel) {
        binding.product = product
        binding.countView.count = product.count
        binding.mainProductLayout.setOnClickListener {
            mainProductClickListener.onProductClick(product)
        }
        binding.countView.plusClickListener = {
            mainProductClickListener.onPlusClick(product, binding.countView.count)
        }
        binding.countView.minusClickListener = {
            mainProductClickListener.onMinusClick(product, binding.countView.count)
        }
    }
}
