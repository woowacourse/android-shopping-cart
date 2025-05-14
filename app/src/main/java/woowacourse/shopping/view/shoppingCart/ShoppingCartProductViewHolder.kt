package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.product.Product

class ShoppingCartProductViewHolder(
    private val binding: ItemShoppingCartProductBinding,
    onRemoveProduct: (product: Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onRemoveProduct = onRemoveProduct
    }

    fun bind(item: Product) {
        binding.product = item
        binding.imageUrl = item.imageUrl
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onRemoveProduct: (Product) -> Unit,
        ): ShoppingCartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartProductBinding.inflate(layoutInflater, parent, false)
            return ShoppingCartProductViewHolder(binding, onRemoveProduct)
        }
    }
}
