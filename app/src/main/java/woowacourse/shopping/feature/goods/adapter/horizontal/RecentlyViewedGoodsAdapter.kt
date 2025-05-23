package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.goods.adapter.vertical.GoodsClickListener

class RecentlyViewedGoodsAdapter(
    private val goodsClickListener: GoodsClickListener,
) : RecyclerView.Adapter<RecentlyViewedGoodsViewHolder>() {
    val items: MutableList<Goods> =
        mutableListOf(
            Goods(
                "1 임시 더미 굿즈",
                13500,
                "https://animate.godohosting.com/Goods/4522776264043.jpg",
            ),
            Goods(
                "2 임시 더미 굿즈",
                13500,
                "https://animate.godohosting.com/Goods/4522776264043.jpg",
            ),
            Goods(
                "3 임시 더미 굿즈",
                13500,
                "https://animate.godohosting.com/Goods/4522776264043.jpg",
            ),
            Goods(
                "4 임시 더미 굿즈",
                13500,
                "https://animate.godohosting.com/Goods/4522776264043.jpg",
            ),
            Goods(
                "5 임시 더미 굿즈",
                13500,
                "https://animate.godohosting.com/Goods/4522776264043.jpg",
            ),
        )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedGoodsViewHolder = RecentlyViewedGoodsViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: RecentlyViewedGoodsViewHolder,
        position: Int,
    ) {
        holder.binding.goods = items[position]
    }

    override fun getItemCount(): Int = items.size
}
