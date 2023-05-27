package woowacourse.shopping.feature.main.product

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMainProductBinding
import woowacourse.shopping.model.ProductUiModel

class MainProductViewHolder(
    private val binding: ItemMainProductBinding,
    private val mainProductClickListener: MainProductClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel) {
        binding.apply {
            this.product = product
            countView.count = product.count
            setCountVisibility(product.count != 0)

            mainProductLayout.setOnClickListener {
                mainProductClickListener.onProductClick(product)
            }
            plusFab.setOnClickListener {
                setCountVisibility(true)
                mainProductClickListener.onPlusClick(product, countView.count)
                countView.count = 1
            }
            countView.plusClickListener = {
                mainProductClickListener.onPlusClick(product, countView.count)
            }
            countView.minusClickListener = {
                val currentCount = countView.count
                if (currentCount == 1) {
                    setCountVisibility(false)
                }
                mainProductClickListener.onMinusClick(product, currentCount)
            }
        }
    }

    private fun setCountVisibility(isCountOn: Boolean) {
        binding.countView.isVisible = isCountOn
        binding.plusFab.isVisible = !isCountOn
    }
}
