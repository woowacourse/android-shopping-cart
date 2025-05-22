package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductViewHolder(
    val binding: ItemProductBinding,
    val onSelectedProduct: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onSelectedProduct = onSelectedProduct
    }

    fun bind(product: Product) {
        binding.product = product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onSelectedProduct: (Product) -> Unit,
        ): ProductViewHolder {
            val binding =
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding, onSelectedProduct)
        }
    }
}
