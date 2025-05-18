package woowacourse.shopping.view.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemInventoryShowMoreBinding
import woowacourse.shopping.view.model.InventoryItem.ShowMore

class ShowMoreViewHolder private constructor(
    private val binding: ItemInventoryShowMoreBinding,
    handler: InventoryEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(
        parent: ViewGroup,
        handler: InventoryEventHandler,
    ) : this (
        ItemInventoryShowMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        handler,
    )

    init {
        binding.handler = handler
    }

    fun bind(item: ShowMore) {
        binding.button = item
    }
}
