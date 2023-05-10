package woowacourse.shopping.view.productlist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.PriceFormatter

class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel, onClick: ProductListAdapter.OnItemClick) {
        binding.product = product
        binding.onItemClick = onClick
        binding.textPrice.text = binding.root.context.getString(R.string.korean_won, PriceFormatter.format(product.price))
        Glide.with(binding.root.context).load(product.imageUrl).into(binding.imgProduct)
    }
}
