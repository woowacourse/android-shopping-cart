package woowacourse.shopping.view.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemInventoryProductBinding
import woowacourse.shopping.view.model.InventoryItem.ProductUiModel

class ProductViewHolder private constructor(
    private val binding: ItemInventoryProductBinding,
    handler: InventoryEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(
        parent: ViewGroup,
        handler: InventoryEventHandler,
    ) : this (
        ItemInventoryProductBinding.inflate(LayoutInflater.from(parent.context)),
        handler,
    )

    init {
        binding.handler = handler
    }

    fun bind(item: ProductUiModel) {
        binding.product = item
    }
}
