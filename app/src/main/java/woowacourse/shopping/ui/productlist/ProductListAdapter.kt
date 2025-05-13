package woowacourse.shopping.ui.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.domain.product.Product

class ProductListAdapter(
    private val items: List<Product>,
    private val productClickListener: ProductClickListener,
) : RecyclerView.Adapter<ProductListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ProductItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.product_item, parent, false)
        return ProductListViewHolder(binding, productClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(item)
    }
}
