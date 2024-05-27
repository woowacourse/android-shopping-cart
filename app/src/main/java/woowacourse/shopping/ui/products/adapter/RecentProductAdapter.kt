package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel

class RecentProductAdapter(
    private val viewModel: ProductContentsViewModel,
) : ListAdapter<Product, RecentProductViewHolder>(RecentProductDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val binding =
            ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentProductViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
