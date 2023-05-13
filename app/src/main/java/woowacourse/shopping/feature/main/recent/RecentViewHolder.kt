package woowacourse.shopping.feature.main.recent

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentViewHolder private constructor(
    private val binding: ItemRecentProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentProduct: RecentProductItemModel) {
        binding.itemModel = recentProduct
    }

    companion object {
        fun create(parent: ViewGroup): RecentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductBinding.inflate(layoutInflater, parent, false)
            return RecentViewHolder(binding)
        }
    }
}
