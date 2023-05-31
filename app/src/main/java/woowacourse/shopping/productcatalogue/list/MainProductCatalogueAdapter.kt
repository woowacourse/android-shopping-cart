package woowacourse.shopping.productcatalogue.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.databinding.RecentProductCatalogueBinding
import woowacourse.shopping.productcatalogue.ProductCountClickListener
import woowacourse.shopping.productcatalogue.list.ProductViewType.MAIN_PRODUCTS
import woowacourse.shopping.productcatalogue.list.ProductViewType.READ_MORE
import woowacourse.shopping.productcatalogue.list.ProductViewType.RECENT_PRODUCTS
import woowacourse.shopping.productcatalogue.recent.RecentProductCatalogueAdapter
import woowacourse.shopping.productcatalogue.recent.RecentProductCatalogueViewHolder
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class MainProductCatalogueAdapter(
    private val productOnClick: ProductClickListener,
    private val productCountOnClick: ProductCountClickListener,
    private val readMoreOnClick: (Int, Int) -> Unit,
    private val getProductCount: (ProductUIModel) -> Int,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val recentProducts = mutableListOf<RecentProductUIModel>()
    val products = mutableListOf<ProductUIModel>()
    val recentAdapter by lazy { RecentProductCatalogueAdapter(productOnClick) }

    fun updateProducts(newProducts: List<ProductUIModel>) {
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> RECENT_PRODUCTS.ordinal
            products.size + FIRST_PAGE -> READ_MORE.ordinal
            else -> MAIN_PRODUCTS.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            RECENT_PRODUCTS.ordinal -> {
                val view = RecentProductCatalogueViewHolder.getView(parent)
                view.rvRecentProductCatalogue.adapter = recentAdapter
                recentAdapter.initProducts(recentProducts)
                RecentProductCatalogueViewHolder(
                    view,
                )
            }
            MAIN_PRODUCTS.ordinal -> {
                MainProductCatalogueViewHolder(
                    MainProductCatalogueViewHolder.getView(parent),
                    productCountOnClick,
                    productOnClick
                )
            }
            READ_MORE.ordinal -> {
                ReadMoreViewHolder(ReadMoreViewHolder.getView(parent), readMoreOnClick, products)
            }
            else -> throw IllegalArgumentException("잘못된 값: $viewType 유효하지 않은 ViewType 입니다.")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            MAIN_PRODUCTS.ordinal -> (holder as MainProductCatalogueViewHolder).bind(
                products[position - 1],
                getProductCount(products[position - 1])
            )
        }
    }

    override fun getItemCount(): Int = products.size + 2

    fun setRecentProductsVisibility(parent: ViewGroup) {
        val inflater = LayoutInflater.from(parent.context)
        val view = RecentProductCatalogueBinding.inflate(inflater, parent, false)
        if (recentProducts.isEmpty()) {
            view.root.layoutParams =
                RecyclerView.LayoutParams(0, 0)
            return
        }
        view.root.layoutParams =
            RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600)
    }

    fun initRecentAdapterData(recentProducts: List<RecentProductUIModel>) {
        recentAdapter.initProducts(recentProducts)
    }

    fun updateRecentProducts(newRecentProducts: List<RecentProductUIModel>) {
        recentProducts.clear()
        recentProducts.addAll(newRecentProducts)
        recentAdapter.initProducts(newRecentProducts)
        recentAdapter.notifyDataSetChanged()
        notifyDataSetChanged()
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}
