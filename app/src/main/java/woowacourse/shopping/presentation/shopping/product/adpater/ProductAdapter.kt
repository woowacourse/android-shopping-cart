package woowacourse.shopping.presentation.shopping.product.adpater

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemPlusProductBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

class ProductAdapter(
    private val onClickItem: (id: Long) -> Unit,
    private val onPlusItem: () -> Unit,
) :
    RecyclerView.Adapter<ShoppingViewHolder>() {
    private var products: List<ShoppingUiModel> = emptyList()

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
                ShoppingViewHolder.ProductViewHolder(view, onClickItem)
            }

            ShoppingUiModel.ITEM_VIEW_TYPE_PLUS -> {
                val view = ItemPlusProductBinding.inflate(layoutInflater, parent, false)
                ShoppingViewHolder.PlusViewHolder(view, onPlusItem)
            }

            else -> error("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: ShoppingViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ShoppingViewHolder.ProductViewHolder -> holder.bind(products[position] as ShoppingUiModel.Product)
            is ShoppingViewHolder.PlusViewHolder -> holder.bind()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<ShoppingUiModel>) {
        this.products = newProducts
        notifyDataSetChanged()
    }
}
