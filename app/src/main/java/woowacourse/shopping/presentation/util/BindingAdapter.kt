package woowacourse.shopping.presentation.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.goods.Goods
import woowacourse.shopping.domain.model.shoppingcart.ShoppingCartItem
import woowacourse.shopping.presentation.goods.list.GoodsAdapter
import woowacourse.shopping.presentation.goods.list.RecentGoodsAdapter
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartAdapter
import java.text.DecimalFormat

@BindingAdapter("goodsPrice")
fun TextView.setPrice(goods: Goods) {
    text = context.getString(R.string.text_price, DecimalFormat("#,###").format(goods.price.value))
}

@BindingAdapter("goodsTotalPrice")
fun TextView.setTotalPrice(item: ShoppingCartItem) {
    text = context.getString(R.string.text_price, DecimalFormat("#,###").format(item.totalPrice))
}

@BindingAdapter("goodsName")
fun TextView.setName(goods: Goods?) {
    if (goods == null) return
    text = goods.name.value
}

@BindingAdapter("goodsImage")
fun ImageView.setImage(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .placeholder(R.drawable.loading)
        .into(this)
}

@BindingAdapter("goodsList")
fun RecyclerView.bindGoods(items: List<ShoppingCartItem>?) {
    (adapter as? GoodsAdapter)?.updateItems(items.orEmpty())
}

@BindingAdapter("selectedGoodsList")
fun RecyclerView.bindSelectedGoods(items: List<ShoppingCartItem>?) {
    (adapter as? ShoppingCartAdapter)?.updateItems(items.orEmpty())
}

@BindingAdapter("recentGoodsList")
fun RecyclerView.bindRecentGoods(items: List<Goods>?) {
    (adapter as? RecentGoodsAdapter)?.updateItems(items.orEmpty())
}
