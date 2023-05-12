package woowacourse.shopping.view.shoppingmain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductMainBinding
import woowacourse.shopping.databinding.RecentProductsBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductsAdapter(
    private var products: List<ProductUIModel>,
    private var recentProducts: List<RecentProductUIModel>,
    private val productOnClick: (ProductUIModel) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                val view = RecentProductsBinding.inflate(inflater, parent, false)
                view.rvRecentProductCatalogue.adapter =
                    RecentProductsAdapter(recentProducts, productOnClick)
                RecentProductsViewHolder(view, recentProducts)
            }
            MAIN_PRODUCTS_VIEW_TYPE -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemProductMainBinding.inflate(inflater, parent, false)
                ProductsViewHolder(view, products, productOnClick)
            }
            else -> throw IllegalArgumentException("잘못된 값: $viewType 유효하지 않은 ViewType입니다.")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {

            MAIN_PRODUCTS_VIEW_TYPE -> (holder as ProductsViewHolder).bind(products[position - 1])
        }
    }

    override fun getItemCount(): Int = products.size

    fun update(
        updatedRecentProducts: List<RecentProductUIModel>,
    ) {
        recentProducts = updatedRecentProducts
    }

    companion object {
        private const val RECENT_PRODUCTS_VIEW_TYPE = 1
        private const val MAIN_PRODUCTS_VIEW_TYPE = 2
    }
}
