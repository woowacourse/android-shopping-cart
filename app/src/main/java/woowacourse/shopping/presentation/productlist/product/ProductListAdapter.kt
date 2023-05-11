package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductContainerBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductAdapter
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductContainerViewHolder

class ProductListAdapter(
    products: List<ProductModel>,
    private val showProductDetail: (ProductModel) -> Unit,
    private val recentProductAdapter: RecentProductAdapter,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemProductBinding: ItemProductBinding
    private lateinit var containerBinding: ItemRecentProductContainerBinding
    private val _products = products.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ProductListViewType.find(viewType)) {
            ProductListViewType.RECENT_PRODUCT -> {
                containerBinding = ItemRecentProductContainerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )

                RecentProductContainerViewHolder(
                    containerBinding,
                    recentProductAdapter,
                )
            }
            ProductListViewType.PRODUCT -> {
                itemProductBinding = ItemProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )

                ProductItemViewHolder(itemProductBinding, showProductDetail)
            }
        }
    }

    override fun getItemCount(): Int = _products.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductItemViewHolder) {
            holder.bind(_products[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == RECENT_PRODUCT_VIEW_POSITION) {
            return ProductListViewType.RECENT_PRODUCT.number
        }
        return ProductListViewType.PRODUCT.number
    }

    fun setItems(products: List<ProductModel>) {
        _products.clear()
        _products.addAll(products)
        notifyDataSetChanged()
    }

    companion object {
        const val RECENT_PRODUCT_VIEW_POSITION = 0
    }
}
