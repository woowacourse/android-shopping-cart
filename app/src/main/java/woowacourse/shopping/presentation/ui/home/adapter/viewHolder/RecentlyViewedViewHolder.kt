package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewedBinding
import woowacourse.shopping.presentation.ui.home.adapter.RecentlyViewedProductAdapter

class RecentlyViewedViewHolder(
    binding: ItemRecentlyViewedBinding,
    adapter: RecentlyViewedProductAdapter,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listRecentlyViewed.adapter = adapter
    }

    companion object {
        fun getView(parent: ViewGroup): ItemRecentlyViewedBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemRecentlyViewedBinding.inflate(layoutInflater, parent, false)
        }
    }
}
