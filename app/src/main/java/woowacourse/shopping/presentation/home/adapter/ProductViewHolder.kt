package woowacourse.shopping.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.home.ProductItemClickListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    productItemClickListener: ProductItemClickListener,
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productItemClickListener = productItemClickListener
    }

    fun bind(product: Product) {
        binding.product = product
    }
}
