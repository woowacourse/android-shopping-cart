package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.model.ProductUiModel

class RecentProductItemViewHolder(
    private val binding: ItemRecentProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel) {
        binding.product = product
    }

    companion object {
        fun from(parent: ViewGroup): RecentProductItemViewHolder {
            val binding =
                ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentProductItemViewHolder(binding)
        }
    }
}
