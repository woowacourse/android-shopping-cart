package woowacourse.shopping.view.product.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductViewHolder(
    val binding: ItemProductBinding,
    val onSelectedProduct: (Product) -> Unit,
    val onAddCart: (Product, Int) -> Unit,
    val viewModel: ProductViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.vm = viewModel
        binding.onAddCart = onAddCart
        binding.lifecycleOwner = binding.root.findViewTreeLifecycleOwner()
        binding.onSelectedProduct = onSelectedProduct
    }

    fun bind(
        product: Product,
        position: Int,
    ) {
        binding.product = product
        binding.position = position
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onSelectedProduct: (Product) -> Unit,
            onAddCart: (Product, Int) -> Unit,
            viewModel: ProductViewModel,
        ): ProductViewHolder {
            val binding =
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding, onSelectedProduct, onAddCart, viewModel)
        }
    }
}
