package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.product.CartItem

class ProductViewHolder(
    private val binding: ItemProductBinding,
    onSelectProduct: (CartItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onSelectProduct = onSelectProduct
    }

    fun bind(item: ProductsItem.ProductItem) {
        binding.product = item.cartItem
        binding.imageUrl = item.cartItem.imageUrl
        binding.quantity = item.quantity

        binding.productAddButton.setOnClickListener {
            item.quantity++
            updateQuantity(item.quantity)
        }

        binding.productQuantityMinusButton.setOnClickListener {
            item.quantity--
            updateQuantity(item.quantity)
        }

        binding.productQuantityPlusButton.setOnClickListener {
            item.quantity++
            updateQuantity(item.quantity)
        }
    }

    private fun updateQuantity(quantity: Int) {
        binding.quantity = quantity
        binding.productAddButton.invalidate()
        binding.productQuantityStepper.invalidate()
    }

    companion object {
        fun of(
            parent: ViewGroup,
            onSelectProduct: (CartItem) -> Unit,
        ): ProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
            return ProductViewHolder(binding, onSelectProduct)
        }
    }
}
