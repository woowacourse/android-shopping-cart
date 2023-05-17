package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductWrapperBinding
import woowacourse.shopping.presentation.view.productlist.adpater.RecentProductListAdapter

class RecentProductWrapperViewHolder(
    parent: ViewGroup,
    adapter: RecentProductListAdapter,
    onScrolled: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_recent_product_wrapper, parent, false)
) {
    private val binding = ItemRecentProductWrapperBinding.bind(itemView)

    init {
        binding.rvRecentProductList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                onScrolled(recyclerView.computeHorizontalScrollOffset())
            }
        })
        binding.rvRecentProductList.adapter = adapter
    }

    fun bind(lastScrollX: Int) {
        binding.rvRecentProductList.doOnLayout {
            binding.rvRecentProductList.scrollBy(lastScrollX, 0)
        }
    }
}
