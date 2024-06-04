package woowacourse.shopping.ui.productList.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.productList.ProductListListener

class ProductsItemViewHolder(
    private val binding: HolderProductBinding,
    private val productListListener: ProductListListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.productListListener = productListListener
    }
}
