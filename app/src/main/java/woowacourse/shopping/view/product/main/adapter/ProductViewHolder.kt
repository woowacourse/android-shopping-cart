package woowacourse.shopping.view.product.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.main.ProductViewModel

class ProductViewHolder(
    val binding: ItemProductBinding,
    val navigateToProductDetail: (Product) -> Unit,
    val onAddCart: (Product, Int) -> Unit,
    val viewModel: ProductViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.vm = viewModel
        binding.onAddCart = onAddCart
        binding.lifecycleOwner = binding.root.findViewTreeLifecycleOwner()
        binding.onSelectedProduct = navigateToProductDetail
    }

    fun bind(product: ViewItems.Products) {
        binding.product = product.product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            navigateToProductDetail: (Product) -> Unit,
            onAddCart: (Product, Int) -> Unit,
            viewModel: ProductViewModel,
        ): ProductViewHolder {
            val binding =
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding, navigateToProductDetail, onAddCart, viewModel)
        }
    }
}
