package woowacourse.shopping.view.products.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.products.OnClickProduct

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val onClickProduct: OnClickProduct,
): RecyclerView.ViewHolder(binding.root){

    fun bind(product: Product){
        binding.product = product
        binding.onClickProduct = onClickProduct
    }
}
