package woowacourse.shopping.ui.products.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentProductsAdapter : RecyclerView.Adapter<RecentProductViewHolder>() {
    private var recentProductUiModels: MutableList<RecentProductUiModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentProductBinding.inflate(inflater, parent, false)
        return RecentProductViewHolder(binding)
    }

    override fun getItemCount(): Int = recentProductUiModels.size

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(recentProductUiModels[position])
    }

    fun updateRecentProduct(recentProducts: List<RecentProductUiModel>) {
        recentProducts.forEachIndexed { position, recentProduct ->
            if (position >= recentProductUiModels.size) {
                insertRangeRecentProducts(recentProducts, position)
                return@forEachIndexed
            }
            changeRecentProduct(recentProduct, position)
        }
    }

    private fun insertRangeRecentProducts(
        newRecentProducts: List<RecentProductUiModel>,
        startPosition: Int,
    ) {
        recentProductUiModels.addAll(newRecentProducts.subList(startPosition, newRecentProducts.size))
        notifyItemRangeInserted(startPosition, newRecentProducts.size - recentProductUiModels.size)
    }

    private fun changeRecentProduct(
        newRecentProduct: RecentProductUiModel,
        position: Int,
    ) {
        if (newRecentProduct == recentProductUiModels[position]) {
            return
        }
        recentProductUiModels[position] = newRecentProduct
        notifyItemChanged(position)
    }
}
