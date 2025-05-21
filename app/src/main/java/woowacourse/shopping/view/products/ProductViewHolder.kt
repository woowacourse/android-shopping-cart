package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding

class ProductViewHolder(
    private val binding: ItemProductBinding,
    productClickListener: ProductsClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productClickListener = productClickListener
    }

    fun bind(item: ProductListViewType.ProductType) {
        binding.product = item.product
    }

    companion object {
        fun create(
            parent: ViewGroup,
            productClickListener: ProductsClickListener,
        ): ProductViewHolder {
            val binding =
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding, productClickListener)
        }
    }
}
