package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemPlusProductBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel
import woowacourse.shopping.presentation.util.ItemUpdateHelper

class ProductAdapter(
    private val onClickProduct: (id: Long) -> Unit,
    private val onClickLoadMore: () -> Unit,
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
                ShoppingViewHolder.Product(view, onClickProduct)
            }

            ShoppingUiModel.ITEM_VIEW_TYPE_PLUS -> {
                val view = ItemPlusProductBinding.inflate(layoutInflater, parent, false)
                ShoppingViewHolder.LoadMore(view, onClickLoadMore)
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
