package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreProductBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.util.ItemUpdateHelper
import woowacourse.shopping.presentation.shopping.product.ProductItemListener
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

class ProductAdapter(
    private val listener: ProductItemListener,
) :
    RecyclerView.Adapter<ShoppingViewHolder>() {
    private var products: List<ShoppingUiModel> = emptyList()
    private val updateHelper: ItemUpdateHelper<ShoppingUiModel> =
        ItemUpdateHelper<ShoppingUiModel>(
            adapter = this,
            areItemsTheSame = { oldItem, newItem ->
                if (oldItem is ShoppingUiModel.Product && newItem is ShoppingUiModel.Product) {
                    return@ItemUpdateHelper (oldItem.id == newItem.id)
                }
                return@ItemUpdateHelper false
            },
            areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
        )

    override fun getItemCount(): Int = products.size

    override fun getItemViewType(position: Int): Int {
        return products[position].viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ShoppingUiModel.ITEM_VIEW_TYPE_PRODUCT -> {
                val view =
                    ItemProductBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                ShoppingViewHolder.Product(view, listener)
            }

            ShoppingUiModel.ITEM_VIEW_TYPE_PLUS -> {
                val view = ItemLoadMoreProductBinding.inflate(layoutInflater, parent, false)
                ShoppingViewHolder.LoadMore(view, listener)
            }

            else -> error("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: ShoppingViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ShoppingViewHolder.Product -> holder.bind(products[position] as ShoppingUiModel.Product)
            is ShoppingViewHolder.LoadMore -> holder.bind()
        }
    }

    fun updateProducts(newProducts: List<ShoppingUiModel>) {
        val oldProducts = products.toList()
        products = newProducts
        updateHelper.update(oldProducts, newProducts)
    }
}
