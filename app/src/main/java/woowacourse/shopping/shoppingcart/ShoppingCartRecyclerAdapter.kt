package woowacourse.shopping.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.productdetail.ProductUiModel

class ShoppingCartRecyclerAdapter(
    products: List<ProductUiModel>,
//    private val onRemoveClicked: (product: ProductUiModel) -> Unit
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
    fun removeItem(position: Int) {
        products.removeAt(position)
        notifyDataSetChanged()
    }
}
