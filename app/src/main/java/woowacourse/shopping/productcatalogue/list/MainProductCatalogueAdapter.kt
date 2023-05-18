package woowacourse.shopping.productcatalogue.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductCatalogueBinding
import woowacourse.shopping.databinding.ItemProductReadMoreBinding
import woowacourse.shopping.databinding.RecentProductCatalogueBinding
import woowacourse.shopping.datas.ProductDataRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.productcatalogue.list.ProductViewType.MAIN_PRODUCTS
import woowacourse.shopping.productcatalogue.list.ProductViewType.READ_MORE
import woowacourse.shopping.productcatalogue.list.ProductViewType.RECENT_PRODUCTS
import woowacourse.shopping.productcatalogue.recent.RecentProductCatalogueAdapter
import woowacourse.shopping.productcatalogue.recent.RecentProductCatalogueViewHolder

class MainProductCatalogueAdapter(
    private val productOnClick: ProductClickListener,
    private val readMoreOnClick: (Int, Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val recentAdapter by lazy { RecentProductCatalogueAdapter(productOnClick, recentProducts) }

    init {
        ProductDataRepository.getUnitData(PRODUCT_UNIT_SIZE, FIRST_PAGE)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> RECENT_PRODUCTS.ordinal
            ProductDataRepository.products.size + FIRST_PAGE -> READ_MORE.ordinal
            else -> MAIN_PRODUCTS.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            RECENT_PRODUCTS.ordinal -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = RecentProductCatalogueBinding.inflate(inflater, parent, false)
                view.rvRecentProductCatalogue.adapter = recentAdapter
                RecentProductCatalogueViewHolder(view, recentProducts.getAll().map { it.toUIModel() })
            }
            MAIN_PRODUCTS.ordinal -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemProductCatalogueBinding.inflate(inflater, parent, false)
                MainProductCatalogueViewHolder(view, productOnClick)
            }
            READ_MORE.ordinal -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemProductReadMoreBinding.inflate(inflater, parent, false)
                ReadMoreViewHolder(view, readMoreOnClick)
            }
            else -> throw IllegalArgumentException("잘못된 값: $viewType 유효하지 않은 ViewType 입니다.")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            MAIN_PRODUCTS.ordinal -> (holder as MainProductCatalogueViewHolder).bind(
                ProductDataRepository.products[position - FIRST_PAGE].toUIModel()
            )
        }
    }

    override fun getItemCount(): Int = ProductDataRepository.products.size + 2

    fun setRecentProductsVisibility(parent: ViewGroup) {
        val inflater = LayoutInflater.from(parent.context)
        val view = RecentProductCatalogueBinding.inflate(inflater, parent, false)
        Log.d("bandal", "${recentProducts.isEmpty()}")
        if (recentProducts.isEmpty()) {
            view.root.layoutParams =
                RecyclerView.LayoutParams(0, 0)
            return
        }
        view.root.layoutParams =
            RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600)
    }

    companion object {
        private const val PRODUCT_UNIT_SIZE = 20
        private const val FIRST_PAGE = 1
    }
}
