package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.list.item.ProductListItem
import java.text.DecimalFormat

class ProductViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    private val binding = binding as ItemProductBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        val productItem = listItem as ProductListItem

        Glide.with(binding.root.context)
            .load(productItem.imageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.productImage)

        binding.productName.text = productItem.name
        binding.productPrice.text = "${DecimalFormat("#,###").format(productItem.price)}원"
        binding.root.setOnClickListener { onClick(productItem) }
    }
}
