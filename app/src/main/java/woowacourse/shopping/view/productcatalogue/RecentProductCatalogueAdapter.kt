package woowacourse.shopping.view.productcatalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductCatalogueRecentBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductCatalogueAdapter(
    private val recentProducts: List<RecentProductUIModel>,
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

    override fun getItemCount(): Int = recentProducts.size

    override fun onBindViewHolder(holder: RecentProductCatalogueChildViewHolder, position: Int) {
        holder.bind(recentProducts[position].productUIModel)
    }
}
