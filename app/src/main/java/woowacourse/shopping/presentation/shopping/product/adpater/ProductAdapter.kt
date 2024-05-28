package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemLoadMoreProductBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.shopping.product.ProductItemListener
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel
import woowacourse.shopping.presentation.util.ItemDiffCallback

class ProductAdapter(
    private val listener: ProductItemListener,
) :
    ListAdapter<ShoppingUiModel, ShoppingViewHolder>(productComparator) {
    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
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
            is ShoppingViewHolder.Product -> holder.bind(currentList[position] as ShoppingUiModel.Product)
            is ShoppingViewHolder.LoadMore -> holder.bind()
        }
    }

    companion object {
        private val productComparator =
            ItemDiffCallback<ShoppingUiModel>(
                onItemsTheSame = { old, new ->
                    if (old is ShoppingUiModel.Product && new is ShoppingUiModel.Product) {
                        return@ItemDiffCallback old.id == new.id
                    }
                    return@ItemDiffCallback false
                },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
