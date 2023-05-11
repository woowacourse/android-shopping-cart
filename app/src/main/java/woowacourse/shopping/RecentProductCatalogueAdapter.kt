package woowacourse.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductCatalogueRecentBinding

class RecentProductCatalogueAdapter(
    private val recentProducts: RecentProductCatalogueUIModel,
    private val productOnClick: (ProductUIModel) -> Unit,
) : RecyclerView.Adapter<RecentProductCatalogueChildViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductCatalogueChildViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductCatalogueRecentBinding.inflate(inflater, parent, false)
        return RecentProductCatalogueChildViewHolder(view, recentProducts, productOnClick)
    }

    override fun getItemCount(): Int = recentProducts.mainProductCatalogue.items.size

    override fun onBindViewHolder(holder: RecentProductCatalogueChildViewHolder, position: Int) {
        holder.bind(recentProducts.mainProductCatalogue.items[position])
    }
}
