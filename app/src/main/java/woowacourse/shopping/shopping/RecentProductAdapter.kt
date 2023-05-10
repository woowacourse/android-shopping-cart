package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.databinding.ItemRecentProductListBinding

class RecentProductAdapter(
    private var recentProducts: List<RecentProductModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRecentProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentProductViewHolder(binding)
    }

    override fun getItemCount(): Int = recentProducts.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecentProductViewHolder).bind(recentProducts[position])
    }

    fun updateRecentProducts(recentProducts: List<RecentProductModel>) {
        this.recentProducts = recentProducts
        notifyDataSetChanged()
    }
}
