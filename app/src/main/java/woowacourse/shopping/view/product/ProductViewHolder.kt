package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.ProductQuantityListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    productListener: ProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productListener = productListener
    }

    fun bind(item: ProductsItem.ProductItem) {
        binding.productItem = item
    }

    companion object {
        fun of(
            parent: ViewGroup,
            productListener: ProductListener,
        ): ProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
            return ProductViewHolder(binding, productListener)
        }
    }

    interface ProductListener : ProductQuantityListener {
        fun onProductClick(product: Product)
    }
}
