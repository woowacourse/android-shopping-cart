package woowacourse.shopping.ui.productList.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.productList.ProductListListener
import woowacourse.shopping.ui.productList.viewholder.ProductsItemViewHolder

class ProductListAdapter(
    private val productListListener: ProductListListener
) : RecyclerView.Adapter<ProductsItemViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsItemViewHolder =
        ProductsItemViewHolder(
            HolderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListListener
        )

    override fun onBindViewHolder(
        holder: ProductsItemViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    // TODO: 최적화
    @SuppressLint("NotifyDataSetChanged")
    fun updateAllLoadedProducts(newData: List<Product>) {
        this.products = newData
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "ProductRecyclerViewAdapter"
    }
}
