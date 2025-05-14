package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.ProductUrls.url
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.product.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    onSelectProduct: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.onSelectProduct = onSelectProduct
    }

    fun bind(item: Product) {
        binding.product = item
        binding.imageUrl = item.url
    }

    companion object {
        fun from(parent: ViewGroup, onSelectProduct: (Product) -> Unit): ProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
            return ProductViewHolder(binding, onSelectProduct)
        }
    }
}
