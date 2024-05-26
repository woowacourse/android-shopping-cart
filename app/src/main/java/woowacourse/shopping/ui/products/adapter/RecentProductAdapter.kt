package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.products.ProductItemClickListener

class RecentProductAdapter(
    private val productItemClickListener: ProductItemClickListener,
) : ListAdapter<Product, RecentProductViewHolder>(RecentProductDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val binding =
            ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentProductViewHolder(binding, productItemClickListener)
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
