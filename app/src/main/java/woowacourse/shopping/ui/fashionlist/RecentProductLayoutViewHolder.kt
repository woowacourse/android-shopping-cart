package woowacourse.shopping.ui.fashionlist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentLayoutBinding
import woowacourse.shopping.domain.product.Product

class RecentProductLayoutViewHolder(
    private val binding: ItemRecentLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: List<Product>) {
        binding.rvRecentProduct.adapter = RecentProductAdapter(item)
        binding.rvRecentProduct.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
    }
}
