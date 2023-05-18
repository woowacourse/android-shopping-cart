package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductContainerBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productlist.product.ProductListViewType.* // ktlint-disable no-wildcard-imports
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductAdapter
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductContainerViewHolder

class ProductListAdapter(
    private val showMoreProductItem: () -> Unit,
    private val showProductDetail: (ProductModel) -> Unit,
    private val recentProductAdapter: RecentProductAdapter,
    private val showCartCount: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemProductBinding: ItemProductBinding
    private lateinit var inflater: LayoutInflater

    private val _products = mutableListOf<ProductModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }

        return when (ProductListViewType.find(viewType)) {
            RECENT_PRODUCT -> {
                val containerBinding = ItemRecentProductContainerBinding.inflate(
                    inflater,
                    parent,
                    false,
                )

                RecentProductContainerViewHolder(
                    containerBinding,
                    recentProductAdapter,
                )
            }
            PRODUCT -> {
                itemProductBinding = ItemProductBinding.inflate(
                    inflater,
                    parent,
                    false,
                )

                ProductItemViewHolder(itemProductBinding, showProductDetail, showCartCount)
            }
            MORE_ITEM -> {
                val itemMoreBinding = ItemMoreBinding.inflate(
                    inflater,
                    parent,
                    false,
                )
                MoreItemViewHolder(itemMoreBinding, showMoreProductItem)
            }
        }
    }

    override fun getItemCount(): Int = _products.size + ADDITIONAL_VIEW_COUNT

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductItemViewHolder) {
            holder.bind(_products[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            RECENT_PRODUCT_VIEW_POSITION -> RECENT_PRODUCT.number
            _products.size + 1 -> MORE_ITEM.number
            else -> PRODUCT.number
        }
    }

    fun setItems(products: List<ProductModel>) {
        _products.clear()
        _products.addAll(products)
        notifyDataSetChanged()
    }

    companion object {
        const val RECENT_PRODUCT_VIEW_POSITION = 0
        private const val ADDITIONAL_VIEW_COUNT = 2
    }
}
