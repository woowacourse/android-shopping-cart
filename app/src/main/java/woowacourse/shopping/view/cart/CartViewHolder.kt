package woowacourse.shopping.view.cart

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.PriceFormatter

class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel) {
        binding.cartProduct = product
        binding.textPrice.text = binding.root.context.getString(R.string.korean_won, PriceFormatter.format(product.price))
        Glide.with(binding.root.context).load(product.imageUrl).into(binding.imgProduct)
        binding.btnClose.setOnClickListener { }
    }
}
