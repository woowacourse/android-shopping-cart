package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductListBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductListViewHolder(
    parent: ViewGroup,
    onProductClick: (Int) -> Unit,
    onCountChanged: (Int, Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from((parent.context))
        .inflate(R.layout.item_product_list, parent, false)
) {
    private val binding = ItemProductListBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            onProductClick(bindingAdapterPosition)
        }

        binding.btProductAdd.setOnClickListener {
            it.visibility = View.GONE
            binding.countViewProductListOrderCount.visibility = View.VISIBLE
            binding.countViewProductListOrderCount.count = 1
            onCountChanged(bindingAdapterPosition, binding.countViewProductListOrderCount.count)
        }

        binding.countViewProductListOrderCount.setOnMinusClickListener {
            if (it.count == 0) {
                binding.btProductAdd.visibility = View.VISIBLE
                binding.countViewProductListOrderCount.visibility = View.GONE
            }
            onCountChanged(bindingAdapterPosition, it.count)
        }

        binding.countViewProductListOrderCount.setOnPlusClickListener {
            onCountChanged(bindingAdapterPosition, it.count)
        }
    }

    fun bind(product: ProductModel) {
        Glide.with(binding.root).load(product.imageUrl).into(binding.ivProductThumbnail)
        binding.tvProductTitle.text = product.title
        binding.tvProductPrice.text =
            binding.root.context.getString(R.string.product_price_format, product.price)
    }
}
