package woowacourse.shopping.feature.product

import android.view.LayoutInflater
import android.view.ViewGroup
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

    companion object {
        fun createInstance(parent: ViewGroup): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding)
        }
    }
}
