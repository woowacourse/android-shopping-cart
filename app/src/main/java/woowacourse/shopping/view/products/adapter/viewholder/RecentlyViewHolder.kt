package woowacourse.shopping.view.products.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyProductBinding
import woowacourse.shopping.domain.model.RecentlyProduct
import woowacourse.shopping.view.products.OnClickProducts

class RecentlyViewHolder(
    private val binding: ItemRecentlyProductBinding,
    private val onClickProducts: OnClickProducts,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentlyProduct: RecentlyProduct) {
        binding.recentlyProduct = recentlyProduct
        binding.onClickProduct = onClickProducts
    }
}
