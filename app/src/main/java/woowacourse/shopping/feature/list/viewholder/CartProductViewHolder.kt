package woowacourse.shopping.feature.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.feature.list.item.CartProductItem
import java.text.DecimalFormat

class CartProductViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    private val binding = binding as ItemCartProductBinding

    fun bind(cartProductItem: CartProductItem, onClick: (CartProductItem) -> Unit) {
        Glide.with(binding.root.context)
            .load(cartProductItem.productImageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.cartProductImageView)

        binding.cartProductNameTv.text = cartProductItem.productName
        binding.cartProductPriceTv.text = "${DecimalFormat("#,###").format(cartProductItem.productPrice)}원"
        binding.cartClearImageView.setOnClickListener { onClick(cartProductItem) }
    }
}
