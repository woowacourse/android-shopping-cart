package woowacourse.shopping.view.shoppingmain

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.uimodel.ProductUIModel

class ProductAdapter(
    private var products: List<ProductUIModel>,
    private val shoppingMainClickListener: ShoppingMainClickListener
) :
    RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent, shoppingMainClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun update(updatedProducts: List<ProductUIModel>) {
        products = products + updatedProducts
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE = 0
    }
}
