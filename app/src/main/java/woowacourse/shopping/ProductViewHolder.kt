package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    onClickHandler: OnClickHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClickHandler = onClickHandler
    }

    fun bind(product: Product) {
        binding.product = product
    }

    interface OnClickHandler {
        fun onClickProduct(id: Int)
    }
}
