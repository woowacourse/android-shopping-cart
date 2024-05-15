package woowacourse.shopping.view.products.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.products.OnClickProducts

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val onClickProducts: OnClickProducts,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.product = product
        binding.onClickProduct = onClickProducts
        Glide.with(itemView.context)
            .load(product.imageUrl)
            .override(Target.SIZE_ORIGINAL)
            .into(binding.ivProductImage)
    }
}
