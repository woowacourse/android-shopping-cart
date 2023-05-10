package woowacourse.shopping.feature.main

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMainProductBinding

class MainProductViewHolder(
    private val binding: ItemMainProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: MainProductItemModel) {
        binding.item = product
        binding.position = bindingAdapterPosition
    }
}