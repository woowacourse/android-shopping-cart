package woowacourse.shopping.feature.product

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.ProductState

class ProductViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    private val binding = binding as ItemProductBinding

    fun bind(productState: ProductState, onClick: (ProductState) -> Unit) {
        binding.product = productState
        binding.root.setOnClickListener { onClick(productState) }
    }
}
