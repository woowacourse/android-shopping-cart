package woowacourse.shopping.feature.product.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.model.RecentProductState

class RecentProductItemViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    val binding = binding as ItemRecentBinding

    fun bind(recentProductState: RecentProductState) {
        binding.recentProduct = recentProductState
    }

    companion object {
        fun createInstance(parent: ViewGroup): RecentProductItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentBinding.inflate(inflater, parent, false)
            return RecentProductItemViewHolder(binding)
        }
    }
}
