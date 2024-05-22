package woowacourse.shopping.view.products.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.cartcounter.OnClickCartItemCounter
import woowacourse.shopping.view.products.OnClickProducts
import woowacourse.shopping.view.products.adapter.viewholder.ProductViewHolder

class ProductAdapter(
    private val onClickProducts: OnClickProducts,
    private val onClickCartItemCounter: OnClickCartItemCounter,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private var products: List<Product> = emptyList()
    private val productPosition: HashMap<Long, Int> = hashMapOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view, onClickCartItemCounter, onClickProducts)
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
        productPosition[item.id] = position
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(addedProducts: List<Product>) {
        val startPosition = products.size
        products = products + addedProducts
        notifyItemRangeInserted(startPosition, addedProducts.size)
    }

    fun updateProduct(productId: Long) {
        val position = productPosition[productId]
        if (position != null) {
            notifyItemChanged(position)
        }
    }
}
