package woowacourse.shopping.ui.products.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentProductsAdapter : RecyclerView.Adapter<RecentProductViewHolder>() {
    private val recentProductUiModels = mutableListOf<RecentProductUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentProductBinding.inflate(inflater, parent, false)
        return RecentProductViewHolder(binding)
    }

    override fun getItemCount(): Int = recentProductUiModels.size

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(recentProductUiModels[position])
    }

    fun insertRecentProduct(recentProducts: List<RecentProductUiModel>) {
        recentProductUiModels.addAll(recentProducts)
        notifyDataSetChanged()
    }
}
