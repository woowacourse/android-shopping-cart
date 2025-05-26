package woowacourse.shopping.view.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyProductBinding
import woowacourse.shopping.domain.Product

class RecentViewHolder(
    private val binding: ItemRecentlyProductBinding,
    private val onRecentProductEventListener: OnRecentProductEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClick = onRecentProductEventListener
    }

    fun bind(product: Product) {
        binding.product = product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onRecentProductEventListener: OnRecentProductEventListener,
        ): RecentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentlyProductBinding.inflate(inflater, parent, false)
            return RecentViewHolder(binding, onRecentProductEventListener)
        }
    }
}
