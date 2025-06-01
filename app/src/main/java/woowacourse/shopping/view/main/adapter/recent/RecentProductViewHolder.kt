package woowacourse.shopping.view.main.adapter.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.view.core.base.BaseViewHolder
import woowacourse.shopping.view.main.adapter.HistoryViewHolder
import woowacourse.shopping.view.main.adapter.ProductRvItems

class RecentProductViewHolder(
    private val parent: ViewGroup,
    private val handler: HistoryViewHolder.Handler,
) : BaseViewHolder<ItemRecentProductBinding>(
        ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    fun bind(item: ProductRvItems.RecentProductItem) {
        val adapter = RecentProductAdapter(item.items, handler)
        val layoutManager = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)

        with(binding) {
            this.adapter = adapter
            recyclerViewRecentProduct.setHasFixedSize(true)
            recyclerViewRecentProduct.itemAnimator = null
            recyclerViewRecentProduct.layoutManager = layoutManager
        }
    }
}
