package woowacourse.shopping.productcatalogue.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductCatalogueRecentBinding
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class RecentProductCatalogueAdapter(
    private val productOnClick: (ProductUIModel) -> Unit,
    private val recentProducts: RecentRepository
) : RecyclerView.Adapter<RecentProductCatalogueChildViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductCatalogueChildViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductCatalogueRecentBinding.inflate(inflater, parent, false)
        return RecentProductCatalogueChildViewHolder(view, recentProducts.getAll().map { it.toUIModel() }, productOnClick)
    }

    override fun getItemCount(): Int = recentProducts.getAll().size

    override fun onBindViewHolder(holder: RecentProductCatalogueChildViewHolder, position: Int) {
        holder.bind(recentProducts.getAll()[position].toUIModel())
    }
}
