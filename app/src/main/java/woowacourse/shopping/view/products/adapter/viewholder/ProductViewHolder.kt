package woowacourse.shopping.view.products.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter
import woowacourse.shopping.view.products.OnClickProducts

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val onClickCartItemCounter: OnClickCartItemCounter,
    private val onClickProducts: OnClickProducts,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.onClickCartItemCounter = onClickCartItemCounter
        binding.onClickProduct = onClickProducts
    }
}
