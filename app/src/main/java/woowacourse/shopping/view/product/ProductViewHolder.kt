package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.product.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    onSelectProduct: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productItemActionListener =
            object : ProductItemActionListener {
                override fun onSelectProduct(item: ProductsItem.ProductItem) {
                    onSelectProduct(item.product)
                }

                override fun onPlusProductQuantity(item: ProductsItem.ProductItem) {
                    item.quantity++
                    binding.invalidateAll()
                }

                override fun onMinusProductQuantity(item: ProductsItem.ProductItem) {
                    item.quantity--
                    binding.invalidateAll()
                }
            }
    }

    fun bind(item: ProductsItem.ProductItem) {
        binding.productItem = item
    }

    companion object {
        fun of(
            parent: ViewGroup,
            onSelectProduct: (Product) -> Unit,
        ): ProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
            return ProductViewHolder(binding, onSelectProduct)
        }
    }
}
