package woowacourse.shopping

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ShoppingRecyclerAdapter(
    private val products: List<ProductUiModel>,
    private val onProductClicked: (ProductUiModel) -> Unit,
) : RecyclerView.Adapter<ShoppingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {

        return ShoppingItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        holder.bind(products[position], onProductClicked)
    }
}
