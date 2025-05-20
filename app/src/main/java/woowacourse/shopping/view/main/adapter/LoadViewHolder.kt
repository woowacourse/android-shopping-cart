package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.view.core.base.BaseViewHolder

class LoadViewHolder(
    parent: ViewGroup,
    private val handler: Handler,
) : BaseViewHolder<ItemLoadBinding>(
        binding =
            ItemLoadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
    ) {
    fun bind(item: ProductRvItems.LoadItem) {
        binding.eventHandler = handler
    }

    interface Handler {
        fun onLoadMoreItems()
    }
}
