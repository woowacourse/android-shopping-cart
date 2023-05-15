package woowacourse.shopping.view.shoppingmain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.RecentProductsBinding

class RecentProductWrapperViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.recent_products, parent, false)
) {
    private val binding = RecentProductsBinding.bind(itemView)

    fun bind(adapter: RecentProductAdapter) {
        binding.rvRecentProductCatalogue.adapter = adapter
    }
}
