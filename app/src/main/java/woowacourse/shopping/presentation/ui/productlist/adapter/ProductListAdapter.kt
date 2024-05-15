package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler

class ProductListAdapter(
    private val actionHandler: ProductListActionHandler,
    private var productList: List<Product> = emptyList(),
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding, actionHandler)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(productList[position])
    }

    fun updateProductList(newProductList: List<Product>) {
        val positionStart = productList.size
        val itemCount = newProductList.size - productList.size

        productList = newProductList
        notifyItemRangeChanged(positionStart, itemCount)
    }

    class ProductViewHolder(
        private val binding: HolderProductBinding,
        private val actionHandler: ProductListActionHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.actionHandler = actionHandler
        }
    }
}
