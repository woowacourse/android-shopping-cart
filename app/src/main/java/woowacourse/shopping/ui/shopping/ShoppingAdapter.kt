package woowacourse.shopping.ui.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductsBinding
import woowacourse.shopping.databinding.ItemShoppingBinding
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.shopping.ShoppingViewHolderType.PRODUCT
import woowacourse.shopping.ui.shopping.ShoppingViewHolderType.RECENT_PRODUCTS
import woowacourse.shopping.ui.shopping.recentproduct.RecentProductsViewHolder

class ShoppingAdapter(
    private val onItemClick: (UiProduct) -> Unit
) : ListAdapter<UiProduct, RecyclerView.ViewHolder>(productDiffUtil) {

    lateinit var layoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) RECENT_PRODUCTS.value
        else PRODUCT.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        return when (ShoppingViewHolderType.getName(viewType)) {
            RECENT_PRODUCTS -> {
                RecentProductsViewHolder(
                    ItemRecentProductsBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    ),
                    onItemClick
                )
            }
            PRODUCT -> {
                ShoppingViewHolder(
                    ItemShoppingBinding.inflate(layoutInflater, parent, false),
                    onItemClick
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            // 레파지토리에서 데이터 불러오는 로직으로 변경 필요
            is RecentProductsViewHolder -> holder.updateRecentProducts(listOf())
            is ShoppingViewHolder -> holder.bind(getItem(position))
        }
    }

    companion object {
        private val productDiffUtil = object : DiffUtil.ItemCallback<UiProduct>() {
            override fun areItemsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem == newItem
        }
    }
}
