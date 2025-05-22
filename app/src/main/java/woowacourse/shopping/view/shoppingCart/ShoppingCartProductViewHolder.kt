package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem

class ShoppingCartProductViewHolder(
    private val binding: ItemShoppingCartProductBinding,
    onRemoveProduct: (cartItem: CartItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onRemoveProduct = onRemoveProduct
    }

    fun bind(item: ProductItem) {
        binding.cartItem = item.cartItem
        binding.imageUrl = item.cartItem.imageUrl
    }

    companion object {
        fun of(
            parent: ViewGroup,
            onRemoveProduct: (CartItem) -> Unit,
        ): ShoppingCartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartProductBinding.inflate(layoutInflater, parent, false)
            return ShoppingCartProductViewHolder(binding, onRemoveProduct)
        }
    }
}
