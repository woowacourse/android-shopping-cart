package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.ProductQuantityClickListener
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem

class ShoppingCartProductViewHolder(
    private val binding: ItemShoppingCartProductBinding,
    shoppingCartListener: ShoppingCartProductClickListener,
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
            shoppingCartListener: ShoppingCartProductClickListener,
        ): ShoppingCartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartProductBinding.inflate(layoutInflater, parent, false)
            return ShoppingCartProductViewHolder(binding, shoppingCartListener)
        }
    }

    interface ShoppingCartProductClickListener : ProductQuantityClickListener {
        fun onRemoveButton(product: Product)
    }
}
