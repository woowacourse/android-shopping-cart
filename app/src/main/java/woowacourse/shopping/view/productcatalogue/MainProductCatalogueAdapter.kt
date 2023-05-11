package woowacourse.shopping.view.productcatalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductCatalogueBinding
import woowacourse.shopping.databinding.RecentProductCatalogueBinding
import woowacourse.shopping.uimodel.MainProductCatalogueUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductCatalogueUIModel

class MainProductCatalogueAdapter(
    private var mainProducts: MainProductCatalogueUIModel,
    private var recentProducts: RecentProductCatalogueUIModel,
    private val productOnClick: (ProductUIModel) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(
        recentProducts: RecentProductCatalogueUIModel,
    ) {
        this.recentProducts = recentProducts
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> RECENT_PRODUCTS_VIEW_TYPE
            else -> MAIN_PRODUCTS_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            RECENT_PRODUCTS_VIEW_TYPE -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = RecentProductCatalogueBinding.inflate(inflater, parent, false)
                view.rvRecentProductCatalogue.adapter =
                    RecentProductCatalogueAdapter(recentProducts, productOnClick)
                RecentProductCatalogueViewHolder(view, recentProducts)
            }
            MAIN_PRODUCTS_VIEW_TYPE -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemProductCatalogueBinding.inflate(inflater, parent, false)
                MainProductCatalogueViewHolder(view, mainProducts, productOnClick)
            }
            else -> throw IllegalArgumentException("잘못된 값: $viewType 유효하지 않은 ViewType입니다.")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {

            MAIN_PRODUCTS_VIEW_TYPE -> (holder as MainProductCatalogueViewHolder).bind(mainProducts.items[position - 1])
        }
    }

    override fun getItemCount(): Int = mainProducts.items.size

    companion object {
        private const val RECENT_PRODUCTS_VIEW_TYPE = 1
        private const val MAIN_PRODUCTS_VIEW_TYPE = 2
    }
}
