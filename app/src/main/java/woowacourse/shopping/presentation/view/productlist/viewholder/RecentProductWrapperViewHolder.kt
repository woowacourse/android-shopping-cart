package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductWrapperBinding
import woowacourse.shopping.presentation.view.productlist.adpater.RecentProductListAdapter

class RecentProductWrapperViewHolder private constructor(view: View) :
    RecyclerView.ViewHolder(view) {
    private val binding = ItemRecentProductWrapperBinding.bind(view)

    constructor(
        parent: ViewGroup,
        adapter: RecentProductListAdapter,
        onScrolled: (Int) -> Unit
    ) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_product_wrapper, parent, false)
    ) {
        binding.rvRecentProductList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                onScrolled(recyclerView.computeHorizontalScrollOffset())
            }
        })
        binding.rvRecentProductList.adapter = adapter
    }
}
