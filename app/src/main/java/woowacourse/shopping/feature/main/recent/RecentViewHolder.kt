package woowacourse.shopping.feature.main.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.RecentProductUiModel

class RecentViewHolder private constructor(
    private val binding: ItemRecentProductBinding,
    recentProductClickListener: RecentProductClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listner = recentProductClickListener
    }

    fun bind(recentProduct: RecentProductUiModel) {
        binding.recentProduct = recentProduct
    }

    companion object {
        fun create(parent: ViewGroup, recentProductClickListener: RecentProductClickListener): RecentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductBinding.inflate(layoutInflater, parent, false)
            return RecentViewHolder(binding, recentProductClickListener)
        }
    }
}
