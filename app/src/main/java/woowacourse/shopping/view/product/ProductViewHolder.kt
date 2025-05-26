package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.ProductQuantityClickListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    productListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productClickListener = productListener
    }

    fun bind(item: ProductsItem.ProductItem) {
        binding.product = item.product
        if (item.selectedQuantity == 0) {
            handleEmptySelectedQuantityVisibility()
            return
        }
        handleSelectedQuantityVisibility(item)
    }

    private fun handleEmptySelectedQuantityVisibility() {
        binding.productQuantityComponentLayout.visibility = View.GONE
        binding.productPlusQuantityButtonDefault.visibility = View.VISIBLE
    }

    private fun handleSelectedQuantityVisibility(item: ProductsItem.ProductItem) {
        binding.productQuantityComponentLayout.visibility = View.VISIBLE
        binding.productPlusQuantityButtonDefault.visibility = View.GONE
        binding.productQuantityComponent.quantity = item.selectedQuantity
    }

    companion object {
        fun of(
            parent: ViewGroup,
            productListener: ProductClickListener,
        ): ProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
            return ProductViewHolder(binding, productListener)
        }
    }

    interface ProductClickListener : ProductQuantityClickListener {
        fun onProductClick(product: Product)
    }
}
