package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.model.ProductUiModel

class RecentViewedViewHolder(
    private val binding: ItemRecentViewedProductBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductUiModel) {
        binding.product = product
    }

    companion object {
        fun from(parent: ViewGroup): RecentViewedViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentViewedProductBinding.inflate(layoutInflater, parent, false)

            return RecentViewedViewHolder(binding)
        }
    }
}
