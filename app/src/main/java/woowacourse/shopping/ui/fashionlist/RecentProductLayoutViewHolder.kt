package woowacourse.shopping.ui.fashionlist

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentLayoutBinding
import woowacourse.shopping.domain.product.Product

class RecentProductLayoutViewHolder(
    private val binding: ItemRecentLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: List<Product>) {
        binding.rvRecentProduct.adapter = RecentProductAdapter(item)
        binding.rvRecentProduct.layoutManager =
            GridLayoutManager(
                binding.root.context,
                1,
                GridLayoutManager.HORIZONTAL,
                false,
            )
    }
}
