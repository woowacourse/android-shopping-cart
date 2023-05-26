package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductListBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.productlist.ProductListener

class ProductListViewHolder(
    parent: ViewGroup,
    productListener: ProductListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from((parent.context))
        .inflate(R.layout.item_product_list, parent, false)
) {
    private val binding = ItemProductListBinding.bind(itemView)

    init {
        binding.productListener = productListener

        binding.productCountView.countStateChangeListener = { count ->
            binding.product?.let { productListener.onCountClick(it.id, count) }
        }
    }

    fun bind(product: ProductModel) {
        binding.product = product
        binding.productCountView.setCount(product.count)
    }
}
