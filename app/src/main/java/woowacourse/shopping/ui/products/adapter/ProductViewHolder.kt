package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.products.ProductItemClickListener
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val productItemClickListener: ProductItemClickListener,
    private val viewModel: ProductContentsViewModel,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(productWithQuantity: ProductWithQuantity) {
        binding.productWithQuantity = productWithQuantity
        binding.productItemClickListener = productItemClickListener
        binding.vm = viewModel
    }
}
