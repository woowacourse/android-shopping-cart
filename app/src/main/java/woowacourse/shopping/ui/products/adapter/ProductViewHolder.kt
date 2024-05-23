package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.CountButtonClickListener
import woowacourse.shopping.ui.StartAddProductClickListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val itemClickListener: (Long) -> Unit,
    private val addRecentProductClickListener: (Long) -> Unit,
    private val plusCountClickListener: (Long) -> Unit,
    private val minusCountClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(productWithQuantity: ProductWithQuantity) {
        binding.productWithQuantity = productWithQuantity
        binding.itemLayout.setOnClickListener {
            itemClickListener(productWithQuantity.product.id)
            addRecentProductClickListener(productWithQuantity.product.id)
        }
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
