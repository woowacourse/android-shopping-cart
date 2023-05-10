package woowacourse.shopping.shopping.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.databinding.ItemRecentProductListBinding

class RecentProductAdapter(
    private var recentProducts: List<RecentProductModel>
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        return RecentProductViewHolder(
            ItemRecentProductListBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = recentProducts.size

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(recentProducts[position])
    }

    fun updateRecentProducts(recentProducts: List<RecentProductModel>) {
        this.recentProducts = recentProducts
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 2
    }
}
