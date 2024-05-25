package woowacourse.shopping.ui.productList

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.OnItemQuantityChangeListener

class ProductsItemViewHolder(
    private val binding: HolderProductBinding,
    private val onProductItemClickListener: ProductRecyclerViewAdapter.OnProductItemClickListener,
    private val onItemQuantityChangeListener: OnItemQuantityChangeListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.onProductItemClickListener = onProductItemClickListener
        binding.onItemChargeListener = onItemQuantityChangeListener
    }
}
