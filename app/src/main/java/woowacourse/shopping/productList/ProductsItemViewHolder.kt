package woowacourse.shopping.productList

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.Product
import woowacourse.shopping.databinding.HolderProductBinding

class ProductsItemViewHolder(
    private val binding: HolderProductBinding,
    private val onProductItemClickListener: ProductRecyclerViewAdapter.OnProductItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.onProductItemClickListener = onProductItemClickListener
    }
}
