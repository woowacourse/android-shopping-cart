package woowacourse.shopping.view.product.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductViewHolder(
    val binding: ItemProductBinding,
    val navigateToProductDetail: (Product) -> Unit,
    val onAddCart: (Product, Int, View) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onAddCart = onAddCart
        binding.onSelectedProduct = navigateToProductDetail
    }

    fun bind(product: ViewItems.Products) {
        binding.product = product.product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            navigateToProductDetail: (Product) -> Unit,
            onAddCart: (Product, Int, View) -> Unit,
        ): ProductViewHolder {
            val binding =
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding, navigateToProductDetail, onAddCart)
        }
    }
}
