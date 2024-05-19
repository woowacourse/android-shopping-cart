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
    private val items = mutableListOf<ProductUiModel>()

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

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        holder.onBind(items[position])
    }

    fun replaceItems(newItems: List<ProductUiModel>) {
        items.clear()
        items.addAll(newItems.toList())
        notifyDataSetChanged()
    }

    fun deleteItemByProductId(productId: Long) {
        val deleteIndex = items.indexOfFirst { it.id == productId }
        items.removeAt(deleteIndex)
        notifyDataSetChanged()
    }

    fun addItem(item: ProductUiModel) {
        items.add(item.copy())
        notifyItemInserted(items.lastIndex)
    }
}
