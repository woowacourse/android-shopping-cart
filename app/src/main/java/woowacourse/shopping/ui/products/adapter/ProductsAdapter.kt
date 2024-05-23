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

    fun insertProducts(insertedProductUiModel: List<ProductUiModel>) {
        productsViews.addAll(insertedProductUiModel)
        notifyDataSetChanged()
//        val positionStart = insertedProductUiModel.size
//        val itemCount = insertedProductUiModel.size - productUiModels.size
//
//        productUiModels.addAll(insertedProductUiModel.subList(productUiModels.size, insertedProductUiModel.size))
//        notifyItemRangeChanged(positionStart, itemCount)
    }

    fun addRecentProducts() {
        productsViews.add(
            0, RecentProductsUiModel(
                listOf(
                    RecentProductUiModel(
                        "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                        "마리오 그린올리브 300g"
                    ),
                    RecentProductUiModel(
                        "https://images.emarteveryday.co.kr/images/product/8801392067167/vSYMPCA3qqbLJjhv.png",
                        "비비고 통새우 만두 200g"
                    ),
                    RecentProductUiModel(
                        "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/97/12/2500000351297_1.png",
                        "스테비아 방울토마토 500g"
                    ),
                    RecentProductUiModel(
                        "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                        "마리오 그린올리브 300g"
                    ),
                    RecentProductUiModel(
                        "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                        "마리오 그린올리브 300g"
                    ),
                )
            )
        )
        notifyItemInserted(0)
    }

    fun replaceProduct(replacedProductUiModel: ProductUiModel) {
        val replacedProductPosition =
            productsViews.indexOfFirst {
                if (it !is ProductUiModel) return@indexOfFirst false
                it.productId == replacedProductUiModel.productId
            }
        productsViews[replacedProductPosition] = replacedProductUiModel
        notifyItemChanged(replacedProductPosition)
    }
}
