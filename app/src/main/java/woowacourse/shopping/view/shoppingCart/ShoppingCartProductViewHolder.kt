package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.product.ProductUrls.url
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.product.Product

class ShoppingCartProductViewHolder(
    private val binding: ItemShoppingCartProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.product = item
        binding.imageUrl = item.url
    }

    companion object {
        fun from(parent: ViewGroup): ShoppingCartProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartProductBinding.inflate(layoutInflater, parent, false)
            return ShoppingCartProductViewHolder(binding)
        }
    }
}
