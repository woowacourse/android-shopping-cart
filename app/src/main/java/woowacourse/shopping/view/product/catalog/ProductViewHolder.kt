package woowacourse.shopping.view.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.mapper.toProductDomain
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.product.OnProductListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    eventListener: OnProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClick = eventListener
        binding.initQuantityControl.onClick = eventListener
        binding.initQuantityPlus.onClick = eventListener
    }

    fun bind(shoppingProduct: ShoppingProduct) {
        val product = shoppingProduct.productId.toProductDomain()
        binding.product = product
        binding.quantity = shoppingProduct.quantity
        binding.initQuantityControl.product = product
        binding.initQuantityControl.shoppingProduct = shoppingProduct
        binding.initQuantityPlus.product = product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: OnProductListener,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding, eventListener)
        }
    }
}
