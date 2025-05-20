package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewHolder(
    private val binding: CartItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: ProductUiModel) {
        binding.cartProduct = cartProduct
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onDeleteProductClick: DeleteProductClickListener,
        ): CartViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CartItemBinding.inflate(inflater, parent, false)
            binding.clickListener = onDeleteProductClick
            return CartViewHolder(binding)
        }
    }
}
