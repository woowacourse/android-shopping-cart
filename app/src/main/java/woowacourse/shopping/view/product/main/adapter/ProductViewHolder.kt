package woowacourse.shopping.view.product.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

class ProductViewHolder(
    val binding: ItemProductBinding,
    val navigateToProductDetail: (Product) -> Unit,
    val onAddCart: (CartItem, Int, View) -> Unit,
    onIncreaseClick: (CartItem) -> Unit,
    onDecreaseClick: (CartItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onAddCart = onAddCart
        binding.onSelectedProduct = navigateToProductDetail
        binding.increase = onIncreaseClick
        binding.decrease = onDecreaseClick
    }

    fun bind(products: ViewItems.Products) {
        binding.cartItem = products.cartItem
    }

    companion object {
        fun from(
            parent: ViewGroup,
            navigateToProductDetail: (Product) -> Unit,
            onAddCart: (CartItem, Int, View) -> Unit,
            onIncreaseClick: (CartItem) -> Unit,
            onDecreaseClick: (CartItem) -> Unit,
        ): ProductViewHolder {
            val binding =
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(
                binding,
                navigateToProductDetail,
                onAddCart,
                onIncreaseClick,
                onDecreaseClick,
            )
        }
    }
}
