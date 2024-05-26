package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.CountButtonClickListener
import woowacourse.shopping.ui.StartAddProductClickListener
import woowacourse.shopping.ui.products.ProductItemClickListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val productItemClickListener: ProductItemClickListener,
    private val plusCountClickListener: (Long) -> Unit,
    private val minusCountClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(productWithQuantity: ProductWithQuantity) {
        binding.productWithQuantity = productWithQuantity
        binding.productItemClickListener = productItemClickListener
        binding.startAddProductClickListener =
            object : StartAddProductClickListener {
                override fun addProduct() {
                    plusCountClickListener(productWithQuantity.product.id)
                }
            }
        binding.countButtonClickListener =
            object : CountButtonClickListener {
                override fun plusCount() {
                    plusCountClickListener(productWithQuantity.product.id)
                }

                override fun minusCount() {
                    minusCountClickListener(productWithQuantity.product.id)
                }
            }
    }
}
