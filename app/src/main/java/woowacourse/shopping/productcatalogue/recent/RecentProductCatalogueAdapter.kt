package woowacourse.shopping.productcatalogue.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.databinding.ItemProductCatalogueRecentBinding
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductCatalogueAdapter(
    private val productOnClick: ProductClickListener,
) : RecyclerView.Adapter<RecentProductCatalogueChildViewHolder>() {
    private val recentProducts = mutableListOf<RecentProductUIModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductCatalogueChildViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductCatalogueRecentBinding.inflate(inflater, parent, false)
        return RecentProductCatalogueChildViewHolder(
            view,
            productOnClick
        )
    }

    override fun getItemCount(): Int = recentProducts.size

    override fun onBindViewHolder(holder: RecentProductCatalogueChildViewHolder, position: Int) {
        holder.bind(recentProducts[position])
    }

    fun initProducts(newProducts: List<RecentProductUIModel>) {
        recentProducts.clear()
        recentProducts.addAll(newProducts)
        notifyDataSetChanged()
    }
}
