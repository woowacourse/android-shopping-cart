package woowacourse.shopping.view.products.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.products.OnClickProduct

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val onClickProduct: OnClickProduct,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.product = product
        binding.onClickProduct = onClickProduct
        Glide.with(itemView.context)
            .load(product.imageUrl)
            .override(Target.SIZE_ORIGINAL)
            .into(binding.ivProductImage)
    }
}
