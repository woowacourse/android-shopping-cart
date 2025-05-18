package woowacourse.shopping.view.inventory

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class InventoryViewHolder(
    private val binding: ItemProductBinding,
    handler: InventoryEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(item: Product) {
        binding.product = item
    }
}
