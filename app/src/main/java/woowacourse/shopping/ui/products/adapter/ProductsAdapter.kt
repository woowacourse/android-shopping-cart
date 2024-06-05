package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductsBinding
import woowacourse.shopping.ui.products.ProductsListener
import woowacourse.shopping.ui.products.adapter.recent.RecentProductUiModel
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel
import woowacourse.shopping.ui.products.adapter.type.ProductsView
import woowacourse.shopping.ui.products.adapter.type.ProductsViewType
import woowacourse.shopping.ui.products.adapter.type.RecentProductsUiModel

class ProductsAdapter(private val productsListener: ProductsListener) :
    ListAdapter<ProductsView, ProductsViewHolder>(diffCallback) {
    private var productsViews: List<ProductsView> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val productsViewType = ProductsViewType.from(viewType)
        return when (productsViewType) {
            ProductsViewType.RECENT_PRODUCTS -> {
                val binding = ItemRecentProductsBinding.inflate(inflater, parent, false)
                ProductsViewHolder.RecentProductsViewHolder(
                    binding,
                    productsListener::onClickRecentProductItem,
                )
            }

            ProductsViewType.PRODUCTS_UI_MODEL -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ProductsViewHolder.ProductViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductsViewHolder.RecentProductsViewHolder -> {
                holder.bind((productsViews[position] as RecentProductsUiModel).recentProductUiModels)
            }

            is ProductsViewHolder.ProductViewHolder -> {
                holder.bind(
                    productsViews[position] as ProductUiModel,
                    productsListener,
                )
            }
        }
    }

    override fun getItemCount(): Int = productsViews.size

    override fun getItemViewType(position: Int): Int = productsViews[position].viewType.type

    fun updateProducts(updatedProducts: List<ProductUiModel>) {
        val newProductsView: MutableList<ProductsView> = updatedProducts.toMutableList()

        if (isExistedRecentProducts()) {
            newProductsView.add(RECENT_PRODUCTS_INDEX, productsViews[RECENT_PRODUCTS_INDEX])
        }

        productsViews = newProductsView
        submitList(productsViews)
    }

    fun updateRecentProducts(recentProducts: List<RecentProductUiModel>) {
        val newRecentProducts = RecentProductsUiModel(recentProducts)
        val newProductsView = productsViews.toMutableList()

        if (isExistedRecentProducts()) {
            newProductsView[RECENT_PRODUCTS_INDEX] = newRecentProducts
        } else {
            newProductsView.add(RECENT_PRODUCTS_INDEX, newRecentProducts)
        }

        productsViews = newProductsView
        submitList(productsViews)
    }

    private fun isExistedRecentProducts(): Boolean {
        if (productsViews.isEmpty()) return false
        return ProductsViewType.from(getItemViewType(RECENT_PRODUCTS_INDEX)) == ProductsViewType.RECENT_PRODUCTS
    }

    fun findProductsLastPosition(lastPosition: Int): Int {
        if (isExistedRecentProducts()) return lastPosition - 1
        return lastPosition
    }

    companion object {
        private const val RECENT_PRODUCTS_INDEX = 0

        private val diffCallback =
            object : DiffUtil.ItemCallback<ProductsView>() {
                override fun areItemsTheSame(
                    oldItem: ProductsView,
                    newItem: ProductsView,
                ): Boolean {
                    if (oldItem.viewType != newItem.viewType) return false
                    if (oldItem is RecentProductsUiModel && newItem is RecentProductsUiModel) {
                        return oldItem.recentProductUiModels == newItem.recentProductUiModels
                    }
                    return (oldItem as ProductUiModel).productId == (newItem as ProductUiModel).productId
                }

                override fun areContentsTheSame(
                    oldItem: ProductsView,
                    newItem: ProductsView,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
