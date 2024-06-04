package woowacourse.shopping.ui.productList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.productList.ProductListListener
import woowacourse.shopping.ui.productList.viewholder.ProductsItemViewHolder

class ProductListAdapter(
    private val productListListener: ProductListListener,
) : ListAdapter<Product, ProductsItemViewHolder>(productComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsItemViewHolder =
        ProductsItemViewHolder(
            HolderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListListener,
        )

    override fun onBindViewHolder(
        holder: ProductsItemViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val productComparator =
            object : DiffUtil.ItemCallback<Product>() {
                override fun areItemsTheSame(
                    oldItem: Product,
                    newItem: Product,
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Product,
                    newItem: Product,
                ): Boolean = oldItem == newItem
            }
    }
}
