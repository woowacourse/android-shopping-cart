package woowacourse.shopping.view.products.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.products.OnClickProducts
import woowacourse.shopping.view.products.adapter.viewholder.ProductViewHolder

class ProductAdapter(
    private val onClickProducts: OnClickProducts,
    private val isLoadLastItem: (Boolean) -> Unit,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view, onClickProducts)
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

        if (position == itemCount - 1) {
            isLoadLastItem(true)
        } else {
            isLoadLastItem(false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(addedProducts: List<Product>) {
        val startPosition = products.size
        products = products + addedProducts
        notifyItemRangeInserted(startPosition, addedProducts.size)
    }
}
