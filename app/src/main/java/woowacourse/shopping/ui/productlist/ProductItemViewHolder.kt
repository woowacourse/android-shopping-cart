package woowacourse.shopping.ui.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ProductItemBinding

class ProductItemViewHolder private constructor(
    private val binding: ProductItemBinding,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productClickListener = productClickListener
    }

    fun bind(item: ProductListViewType.ProductItemType) {
        binding.product = item.product
    }

    companion object {
        fun create(
            parent: ViewGroup,
            productClickListener: ProductClickListener
        ): ProductItemViewHolder {
            return ProductItemViewHolder(
                binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.product_item, parent, false
                ),
                productClickListener = productClickListener
            )
        }
    }
}
