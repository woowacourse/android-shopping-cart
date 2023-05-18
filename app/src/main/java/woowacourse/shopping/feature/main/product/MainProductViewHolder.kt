package woowacourse.shopping.feature.main.product

import android.view.View
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
            setVisibility(product.count != 0)

            mainProductLayout.setOnClickListener {
                mainProductClickListener.onProductClick(product)
            }
            plusFab.setOnClickListener {
                setVisibility(true)
                mainProductClickListener.onPlusClick(product, countView.count)
                countView.count = 1
            }
            countView.plusClickListener = {
                mainProductClickListener.onPlusClick(product, countView.count)
            }
            countView.minusClickListener = {
                val currentCount = countView.count
                if (currentCount == 1) {
                    setVisibility(false)
                }
                mainProductClickListener.onMinusClick(product, currentCount)
            }
        }
    }

    private fun setVisibility(isCountOn: Boolean) {
        if (!isCountOn) {
            binding.countView.visibility = View.GONE
            binding.plusFab.visibility = View.VISIBLE
        } else {
            binding.countView.visibility = View.VISIBLE
            binding.plusFab.visibility = View.GONE
        }
    }
}
