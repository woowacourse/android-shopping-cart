package woowacourse.shopping.view.product.catalog.allproducts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.view.product.OnQuantityControlListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    onQuantityControlListener: OnQuantityControlListener,
    categoryEventListener: OnCategoryEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClick = categoryEventListener
        binding.initQuantityControl.onClick = onQuantityControlListener
        binding.initQuantityPlus.onClick = onQuantityControlListener
    }

    fun bind(productItem: ProductItem.CatalogProduct) {
        val product = productItem.product
        binding.product = product
        binding.quantity = productItem.quantity
        binding.initQuantityControl.productId = product.id
        binding.initQuantityControl.quantity = productItem.quantity
        binding.initQuantityPlus.productId = product.id
    }

    companion object {
        fun from(
            parent: ViewGroup,
            productListener: OnQuantityControlListener,
            categoryEventListener: OnCategoryEventListener,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding, productListener, categoryEventListener)
        }
    }
}
