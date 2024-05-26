package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.products.ProductItemClickListener
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel

class ProductAdapter(
    private val productItemClickListener: ProductItemClickListener,
    private val viewModel: ProductContentsViewModel,
) : ListAdapter<ProductWithQuantity, ProductViewHolder>(ProductDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(
            binding,
            productItemClickListener,
            viewModel,
        )
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
