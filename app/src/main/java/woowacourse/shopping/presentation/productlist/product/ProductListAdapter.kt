package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.ProductViewType
import woowacourse.shopping.presentation.model.ProductViewType.MoreItem
import woowacourse.shopping.presentation.model.ProductViewType.ProductItem
import woowacourse.shopping.presentation.model.ProductViewType.RecentProductModels

class ProductListAdapter(
    productItems: List<ProductViewType>,
    private val showMoreProductItem: () -> Unit,
    private val productClickListener: ProductClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var inflater: LayoutInflater

    private val _productItems = productItems.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        initLayoutInflater(parent)

        return when (viewType) {
            ProductViewType.RECENT_PRODUCTS_VIEW_TYPE_NUMBER ->
                RecentProductContainerViewHolder(parent, inflater)

            ProductViewType.PRODUCT_VIEW_TYPE_NUMBER ->
                ProductItemViewHolder(parent, inflater, productClickListener)

            ProductViewType.MORE_ITEM_VIEW_TYPE_NUMBER ->
                MoreItemViewHolder(parent, inflater, showMoreProductItem)

            else -> throw IllegalArgumentException()
        }
    }

    private fun initLayoutInflater(parent: ViewGroup) {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
    }

    override fun getItemCount(): Int = _productItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentProductContainerViewHolder -> {
                holder.bind(
                    _productItems[position] as RecentProductModels,
                    productClickListener::onItemClick,
                )
            }
            is ProductItemViewHolder -> {
                val productItem = _productItems[position] as ProductItem
                holder.bind(productItem.cartProductModel)
            }
            is MoreItemViewHolder -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (_productItems[position]) {
            is RecentProductModels -> ProductViewType.RECENT_PRODUCTS_VIEW_TYPE_NUMBER
            is MoreItem -> ProductViewType.MORE_ITEM_VIEW_TYPE_NUMBER
            else -> ProductViewType.PRODUCT_VIEW_TYPE_NUMBER
        }
    }

    fun setProductItems(productItems: List<ProductItem>) {
        val beforeLastIndex = _productItems.lastIndex
        _productItems.addAll(
            beforeLastIndex,
            productItems,
        )
        notifyItemRangeInserted(beforeLastIndex, productItems.size)
    }

    fun replaceProductItem(productItem: ProductItem) {
        val targetIndex = _productItems.indexOfFirst { productViewType ->
            isSameProductModel(productViewType, productItem)
        }
        if (targetIndex != NOT_FOUNT) {
            _productItems[targetIndex] = productItem
            notifyItemChanged(targetIndex)
        }
    }

    private fun isSameProductModel(
        productViewType: ProductViewType,
        productItem: ProductItem,
    ): Boolean {
        if (productViewType is ProductItem) {
            return isSameProductModel(productViewType, productItem)
        }
        return false
    }

    private fun isSameProductModel(targetItem: ProductItem, productItem: ProductItem) =
        targetItem.cartProductModel.productModel == productItem.cartProductModel.productModel

    fun setRecentProductsItems(productModel: List<ProductModel>) {
        if (_productItems[RECENT_PRODUCT_VIEW_POSITION] is RecentProductModels) {
            _productItems[RECENT_PRODUCT_VIEW_POSITION] = RecentProductModels(productModel)
            notifyItemChanged(RECENT_PRODUCT_VIEW_POSITION)
        } else {
            _productItems.add(RECENT_PRODUCT_VIEW_POSITION, RecentProductModels(productModel))
            notifyItemInserted(RECENT_PRODUCT_VIEW_POSITION)
        }
    }

    fun getRecentFirstProduct(): Long? {
        if (_productItems[RECENT_PRODUCT_VIEW_POSITION] is RecentProductModels) {
            val recentProductModels =
                _productItems[RECENT_PRODUCT_VIEW_POSITION] as RecentProductModels
            return recentProductModels.recentProducts[0].id
        }
        return null
    }

    companion object {
        const val RECENT_PRODUCT_VIEW_POSITION = 0
        const val NOT_FOUNT = -1
    }
}
