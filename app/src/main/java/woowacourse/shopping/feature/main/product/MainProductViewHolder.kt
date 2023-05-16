package woowacourse.shopping.feature.main.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMainProductBinding
import woowacourse.shopping.model.ProductUiModel

class MainProductViewHolder private constructor(
    private val binding: ItemMainProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel, listener: ProductClickListener) {
        binding.product = product
        binding.listener = listener
    }

    companion object {
        fun create(parent: ViewGroup): MainProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMainProductBinding.inflate(layoutInflater, parent, false)
            return MainProductViewHolder(binding)
        }
    }
}
