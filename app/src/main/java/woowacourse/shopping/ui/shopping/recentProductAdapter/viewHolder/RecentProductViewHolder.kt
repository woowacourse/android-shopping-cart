package woowacourse.shopping.ui.shopping.recentProductAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.ui.shopping.recentProductAdapter.RecentProductItem
import woowacourse.shopping.ui.shopping.recentProductAdapter.RecentProductsListener

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    onClickListener: RecentProductsListener
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = onClickListener
    }

    fun bind(recentProduct: RecentProductItem) {
        binding.product = recentProduct.product
    }

    companion object {
        fun from(parent: ViewGroup, onClickListener: RecentProductsListener): RecentProductViewHolder {
            val binding = ItemRecentProductBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentProductViewHolder(binding, onClickListener)
        }
    }
}
