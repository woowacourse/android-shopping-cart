package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.product.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val productClickListener: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.product = item
        binding.btnSelectedProduct.setOnClickListener {
            productClickListener(item)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            clickListener: (Product) -> Unit,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding, clickListener)
        }
    }
}
