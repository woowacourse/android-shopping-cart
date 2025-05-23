package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.view.uimodel.ProductUiModel

class RecentProductsViewHolder(
    private val parent: ViewGroup,
    private val handler: ProductEventHandler,
    private val binding: ItemRecentProductBinding = inflate(parent),
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(item: ProductUiModel) {
        binding.product = item
    }

    companion object {
        fun inflate(parent: ViewGroup): ItemRecentProductBinding {
            return ItemRecentProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        }
    }
}
