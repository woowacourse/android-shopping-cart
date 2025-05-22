package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.product.CartItem

class ShoppingCartProductViewHolder(
    private val binding: ItemShoppingCartProductBinding,
    onRemoveProduct: (cartItem: CartItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.shoppingCartItemActionListener =
            object : ShoppingCartItemActionListener {
                override fun onRemoveProduct(item: ShoppingCartItem.ProductItem) {
                    onRemoveProduct(item.cartItem)
                }

                override fun onPlusProductQuantity(item: ShoppingCartItem.ProductItem) {
                    item.quantity++
                    binding.invalidateAll()
                }

                override fun onMinusProductQuantity(item: ShoppingCartItem.ProductItem) {
                    if (item.quantity == 1) {
                        onRemoveProduct(item)
                        return
                    }

                    item.quantity--
                    binding.invalidateAll()
                }
            }
    }

    fun bind(item: ShoppingCartItem.ProductItem) {
        binding.productItem = item
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
