package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.product.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    productListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productClickListener = productListener
    }

    fun bind(item: ProductsItem.ProductItem) {
        binding.product = item.product
    }

    companion object {
        fun of(
            parent: ViewGroup,
            productListener: ProductClickListener,
        ): ProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
            return ProductViewHolder(binding, productListener)
        }
    }

    interface ProductClickListener {
        fun onProductClick(product: Product)

        fun onPlusShoppingCartClick(product: Product)

        fun onMinusShoppingCartClick(product: Product)
    }
}
