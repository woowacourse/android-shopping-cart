package woowacourse.shopping.ui.products.recent

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemRecentProductsBinding

class RecentProductsViewHolder(
    binding: ItemRecentProductsBinding,
    onClickRecentProductItem: OnClickRecentProductItem,
) : ViewHolder(binding.root) {
    private val adapter: RecentProductsAdapter = RecentProductsAdapter(onClickRecentProductItem)

    init {
        binding.rvRecentProduct.itemAnimator = null
        binding.rvRecentProduct.adapter = adapter
    }

    fun bind(recentProducts: List<RecentProductUiModel>) {
        adapter.updateRecentProduct(recentProducts)
    }
}
