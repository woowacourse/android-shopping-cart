package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUiModel

class ShoppingCartRecyclerAdapter(
    products: List<ProductUiModel>,
    private val onRemoved: (id: Int) -> Unit
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {
    private val products: MutableList<ProductUiModel> = products.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {

        return ShoppingCartItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        holder.bind(
            productUiModel = products[position],
            onRemoveClicked = ::removeItem
        )
    }

    override fun getItemCount(): Int = products.size

    @SuppressLint("NotifyDataSetChanged")
    private fun removeItem(position: Int) {
        onRemoved(products[position].id)
        products.removeAt(position)
        notifyDataSetChanged()
    }
}
