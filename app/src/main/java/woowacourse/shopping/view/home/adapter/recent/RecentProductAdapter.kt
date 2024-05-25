package woowacourse.shopping.view.home.adapter.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.view.home.HomeClickListener

class RecentProductAdapter(private val clickListener: HomeClickListener) :
    RecyclerView.Adapter<RecentProductViewHolder>() {
    private var recentProducts: List<RecentProduct> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val binding =
            ItemRecentProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return RecentProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recentProducts.size
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        val recentViewedProduct = recentProducts[position]
        return holder.bind(recentViewedProduct, clickListener)
    }

    fun loadData(recentProducts: List<RecentProduct>) {
        this.recentProducts = recentProducts
        notifyDataSetChanged()
    }
}
