package woowacourse.shopping.view.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.products.ProductListActionHandler
import woowacourse.shopping.view.products.adapter.viewholder.ProductViewHolder

class ProductAdapter(
    private val productListActionHandler: ProductListActionHandler,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view, productListActionHandler)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        val item = products[position]
        holder.bind(item)

//        if (position == itemCount - 1) {
//            productActionListener.showMoreButton(true)
//        } else {
//            productActionListener.showMoreButton(false)
//        }
    }

    fun updateProducts(addedProducts: List<Product>) {
        val startPosition = products.size
        products = products + addedProducts
        notifyItemRangeInserted(startPosition, addedProducts.size)
    }
}
