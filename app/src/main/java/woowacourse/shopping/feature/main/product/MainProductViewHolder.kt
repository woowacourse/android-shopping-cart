package woowacourse.shopping.feature.main.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMainProductBinding
import woowacourse.shopping.model.ProductUiModel

class MainProductViewHolder(
    private val binding: ItemMainProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel, onClick: (product: ProductUiModel) -> Unit) {
        binding.product = product
        binding.mainProductLayout.setOnClickListener { onClick.invoke(product) }
    }
}
