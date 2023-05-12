package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductContainerBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.ProductViewType
import woowacourse.shopping.presentation.model.ProductViewType.ProductItem
import woowacourse.shopping.presentation.model.ProductViewType.RecentProductModels

class ProductListAdapter(
    productItems: List<ProductViewType>,
    private val showMoreProductItem: () -> Unit,
    private val showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemProductBinding: ItemProductBinding
    private lateinit var inflater: LayoutInflater

    private val _productItems = productItems.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }

        return when (viewType) {
            ProductViewType.RECENT_PRODUCTS_VIEW_TYPE_NUMBER -> {
                val containerBinding = ItemRecentProductContainerBinding.inflate(
                    inflater,
                    parent,
                    false,
                )
                RecentProductContainerViewHolder(containerBinding)
            }
            ProductViewType.PRODUCT_VIEW_TYPE_NUMBER -> {
                itemProductBinding = ItemProductBinding.inflate(
                    inflater,
                    parent,
                    false,
                )

                ProductItemViewHolder(itemProductBinding, showProductDetail)
            }
            ProductViewType.MORE_ITEM_VIEW_TYPE_NUMBER -> {
                val itemMoreBinding = ItemMoreBinding.inflate(
                    inflater,
                    parent,
                    false,
                )
                MoreItemViewHolder(itemMoreBinding, showMoreProductItem)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = _productItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentProductContainerViewHolder -> {
                holder.bind(
                    _productItems[position] as RecentProductModels,
                    showProductDetail,
                )
            }
            is ProductItemViewHolder -> {
                val productItem = _productItems[position] as ProductItem
                holder.bind(productItem.productModel)
            }
            is MoreItemViewHolder -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            RECENT_PRODUCT_VIEW_POSITION -> ProductViewType.RECENT_PRODUCTS_VIEW_TYPE_NUMBER
            _productItems.lastIndex -> ProductViewType.MORE_ITEM_VIEW_TYPE_NUMBER
            else -> ProductViewType.PRODUCT_VIEW_TYPE_NUMBER
        }
    }

    fun setProductItems(products: List<ProductModel>) {
        val beforeLastIndex = _productItems.lastIndex
        _productItems.addAll(
            beforeLastIndex,
            products.map { ProductItem(it) },
        )
        notifyItemRangeChanged(beforeLastIndex, _productItems.lastIndex)
    }

    fun setRecentProductsItems(productModel: List<ProductModel>) {
        _productItems[RECENT_PRODUCT_VIEW_POSITION] =
            RecentProductModels(productModel)
        notifyItemChanged(RECENT_PRODUCT_VIEW_POSITION)
    }

    companion object {
        const val RECENT_PRODUCT_VIEW_POSITION = 0
    }
}
