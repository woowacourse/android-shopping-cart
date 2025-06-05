package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyGoodsBinding
import woowacourse.shopping.feature.goods.adapter.vertical.GoodsClickListener

class RecentlyViewedGoodsViewHolder(
    val binding: ItemRecentlyGoodsBinding,
    recentlyGoodsClickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.clickListener = recentlyGoodsClickListener
    }

    companion object {
        fun from(
            parent: ViewGroup,
            recentlyGoodsClickListener: GoodsClickListener,
            lifecycleOwner: LifecycleOwner,
        ): RecentlyViewedGoodsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentlyGoodsBinding.inflate(inflater, parent, false)
            binding.lifecycleOwner = lifecycleOwner
            return RecentlyViewedGoodsViewHolder(binding, recentlyGoodsClickListener)
        }
    }
}
