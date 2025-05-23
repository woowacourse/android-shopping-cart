package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyGoodsBinding

class RecentlyViewedGoodsViewHolder(
    val binding: ItemRecentlyGoodsBinding,
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): RecentlyViewedGoodsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentlyGoodsBinding.inflate(inflater, parent, false)
            return RecentlyViewedGoodsViewHolder(binding)
        }
    }
}
