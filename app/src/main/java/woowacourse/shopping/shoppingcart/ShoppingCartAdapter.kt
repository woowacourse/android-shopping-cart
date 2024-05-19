package woowacourse.shopping.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.productlist.ProductUiModel
import woowacourse.shopping.util.imageUrlToSrc

class ShoppingCartAdapter(
    private val onClicked: ShoppingCartClickAction,
) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {
    private var items = emptyList<ProductUiModel>()

    class ShoppingCartViewHolder(
        private val binding: ItemCartBinding,
        private val onClicked: ShoppingCartClickAction,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ProductUiModel) {
            with(binding) {
                tvItemCartName.text = item.name
                itemView.context.imageUrlToSrc(item.imageUrl, ivCartProduct)
                tvCartPrice.text =
                    itemView.context.getString(R.string.product_price_format, item.price)
                ibCartClose.setOnClickListener {
                    onClicked.onItemRemoveBtnClicked(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding, onClicked)
    }

    fun submitList(updatedItems: List<ProductUiModel>) {
        items = updatedItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        holder.onBind(items[position])
    }
}
