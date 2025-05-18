package woowacourse.shopping.view.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ButtonLoadMoreProductsBinding
import woowacourse.shopping.view.model.InventoryItem.ShowMoreButton

class ShowMoreViewHolder private constructor(
    private val binding: ButtonLoadMoreProductsBinding,
    handler: InventoryEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(
        parent: ViewGroup,
        handler: InventoryEventHandler,
    ) : this (
        ButtonLoadMoreProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        handler,
    )

    init {
        binding.handler = handler
    }

    fun bind(item: ShowMoreButton) {
        binding.button = item
    }
}
