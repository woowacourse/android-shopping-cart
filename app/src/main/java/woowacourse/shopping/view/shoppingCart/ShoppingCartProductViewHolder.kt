package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.ProductQuantityListener
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem

class ShoppingCartProductViewHolder(
    private val binding: ItemShoppingCartProductBinding,
    shoppingCartListener: ShoppingCartProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.shoppingCartProductClickListener = shoppingCartListener
    }

    fun bind(item: ProductItem) {
        binding.shoppingCartProduct = item.shoppingCartProduct
    }

    companion object {
        fun of(
            parent: ViewGroup,
            shoppingCartListener: ShoppingCartProductListener,
        ): ShoppingCartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartProductBinding.inflate(layoutInflater, parent, false)
            return ShoppingCartProductViewHolder(binding, shoppingCartListener)
        }
    }

    interface ShoppingCartProductListener : ProductQuantityListener {
        fun onRemoveButton(product: Product)
    }
}
