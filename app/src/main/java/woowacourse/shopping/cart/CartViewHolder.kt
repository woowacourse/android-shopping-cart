package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewHolder(
    private val binding: CartItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartProduct: ProductUiModel) {
        binding.cartProduct = cartProduct
        bindImage(cartProduct)
    }

    private fun bindImage(product: ProductUiModel) {
        Glide
            .with(binding.root)
            .load(product.imageUrl)
            .placeholder(R.drawable.iced_americano)
            .fallback(R.drawable.iced_americano)
            .error(R.drawable.iced_americano)
            .into(binding.imageViewCartProduct)
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
