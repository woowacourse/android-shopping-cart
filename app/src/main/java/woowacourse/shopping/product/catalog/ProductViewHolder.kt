package woowacourse.shopping.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding

class ProductViewHolder(
    private val binding: ProductItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
    }

    companion object {
        fun from(parent: ViewGroup): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductItemBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding)
        }
    }
}
