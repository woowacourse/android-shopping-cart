package woowacourse.shopping.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingBinding
import woowacourse.shopping.ui.model.UiProduct

class ShoppingViewHolder(private val binding: ItemShoppingBinding, onItemClick: (UiProduct) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var product: UiProduct

    init {
        binding.root.setOnClickListener { onItemClick(product) }
    }

    fun bind(item: UiProduct) {
        product = item
        binding.product = item
    }
}
