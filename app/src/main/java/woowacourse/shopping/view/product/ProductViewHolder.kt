package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.product.Product

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
        binding.productPlusQuantityButton.visibility = View.GONE
        binding.productMinusQuantityButton.visibility = View.GONE
        binding.productQuantity.visibility = View.GONE
        binding.productPlusQuantityButtonDefault.visibility = View.VISIBLE
    }

    private fun handleSelectedQuantityVisibility(item: ProductsItem.ProductItem) {
        binding.productPlusQuantityButton.visibility = View.VISIBLE
        binding.productMinusQuantityButton.visibility = View.VISIBLE
        binding.productQuantity.visibility = View.VISIBLE
        binding.productPlusQuantityButtonDefault.visibility = View.GONE
        binding.productQuantity.text = item.selectedQuantity.toString()
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

    interface ProductClickListener {
        fun onProductClick(product: Product)

        fun onPlusShoppingCartClick(product: Product)

        fun onMinusShoppingCartClick(product: Product)
    }
}
