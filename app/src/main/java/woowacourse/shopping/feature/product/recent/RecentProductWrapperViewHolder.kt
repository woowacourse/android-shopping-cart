package woowacourse.shopping.feature.product.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding

class RecentProductWrapperViewHolder(
    private val binding: ViewBinding,
    private val adapter: RecentProductListAdapter
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding as ItemRecentProductListBinding
        binding.recentListRv.adapter = adapter
    }

    companion object {
        fun createInstance(parent: ViewGroup, recentProductListAdapter: RecentProductListAdapter): RecentProductWrapperViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductListBinding.inflate(inflater, parent, false)
            return RecentProductWrapperViewHolder(binding, recentProductListAdapter)
        }
    }
}
