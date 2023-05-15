package woowacourse.shopping.list.viewholder

import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.list.item.CartProductListItem
import woowacourse.shopping.list.item.ListItem
import java.text.DecimalFormat

class CartProductViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    private val binding = binding as ItemCartProductBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        val cartItem = listItem as CartProductListItem

        Glide.with(binding.root.context)
            .load(cartItem.productImageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.cartProductImageView)

        binding.cartProductNameTv.text = cartItem.productName
        binding.cartProductPriceTv.text = "${DecimalFormat("#,###").format(cartItem.productPrice)}원"
        binding.cartClearImageView.setOnClickListener { onClick(cartItem) }
    }
}
