package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductsBinding
import woowacourse.shopping.ui.products.ProductUiModel
import woowacourse.shopping.ui.products.ProductsView
import woowacourse.shopping.ui.products.recent.RecentProductUiModel
import woowacourse.shopping.ui.products.RecentProductsUiModel
import woowacourse.shopping.ui.products.recent.RecentProductsViewHolder
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class ProductsAdapter(
    private val onClickProductItem: OnClickProductItem,
    private val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    private val onDecreaseProductQuantity: OnDecreaseProductQuantity,
) :
    RecyclerView.Adapter<ViewHolder>() {
    private val productsViews: MutableList<ProductsView> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val productsViewType = ProductsViewType.from(viewType)
        return when (productsViewType) {
            ProductsViewType.RECENT_PRODUCTS -> {
                val binding = ItemRecentProductsBinding.inflate(inflater, parent, false)
                RecentProductsViewHolder(binding)
            }

            ProductsViewType.PRODUCTS_UI_MODEL -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ProductsViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (productsViews[position].viewType) {
            ProductsViewType.RECENT_PRODUCTS -> (holder as RecentProductsViewHolder).bind(
                (productsViews[position] as RecentProductsUiModel).recentProductUiModels,
            )
            ProductsViewType.PRODUCTS_UI_MODEL -> (holder as ProductsViewHolder).bind(
                productsViews[position] as ProductUiModel,
                onClickProductItem,
                onIncreaseProductQuantity,
                onDecreaseProductQuantity,
            )
        }
    }

    override fun getItemCount(): Int {
        return productsViews.size
    }

    override fun getItemViewType(position: Int): Int = productsViews[position].viewType.type

    fun updateProductUiModels(updatedProductUiModel: List<ProductUiModel>) {
        val productUiModels = productsViews.filterIsInstance<ProductUiModel>()
        val newProductUiModels = updatedProductUiModel.subtract(productUiModels.toSet())

        if (productUiModels.size < updatedProductUiModel.size) {
            insertRangeProductUiModels(newProductUiModels)
            return
        }

        if (productUiModels.size == updatedProductUiModel.size) {
            newProductUiModels.forEach { changeProductUiModel(it) }
        }
    }

    private fun insertRangeProductUiModels(newProductUiModels: Set<ProductUiModel>) {
        productsViews.addAll(newProductUiModels)
        notifyItemRangeInserted(productsViews.size, newProductUiModels.size)
    }

    private fun changeProductUiModel(newProductUiModel: ProductUiModel) {
        val position = productsViews.indexOfFirst { it is ProductUiModel && it.productId == newProductUiModel.productId }
        productsViews[position] = newProductUiModel
        notifyItemChanged(position)
    }

    fun addRecentProducts(recentProductUiModels: List<RecentProductUiModel>) {
        if (ProductsViewType.from(getItemViewType(RECENT_PRODUCTS_INDEX)) == ProductsViewType.RECENT_PRODUCTS) {
            productsViews[RECENT_PRODUCTS_INDEX] = RecentProductsUiModel(recentProductUiModels)
            notifyItemChanged(RECENT_PRODUCTS_INDEX)
            return
        }
        productsViews.add(RECENT_PRODUCTS_INDEX, RecentProductsUiModel(recentProductUiModels))
        notifyItemInserted(RECENT_PRODUCTS_INDEX)
    }

    companion object {
        private const val RECENT_PRODUCTS_INDEX = 0
    }
}
