package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.list.item.ItemState
import woowacourse.shopping.feature.list.item.ProductItem
import java.text.DecimalFormat

class ProductViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    private val binding = binding as ItemProductBinding

    override fun bind(itemState: ItemState, onClick: () -> Unit) {
        val productItem = itemState as ProductItem

        Glide.with(binding.root.context)
            .load(productItem.imageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.productImage)

        binding.productName.text = productItem.name
        binding.productPrice.text = "${DecimalFormat("#,###").format(productItem.price)}원"
        binding.root.setOnClickListener { onClick() }
    }
}
