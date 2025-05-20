package woowacourse.shopping.presentation.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.presentation.goods.list.GoodsAdapter
import woowacourse.shopping.presentation.model.GoodsUiModel
import java.text.DecimalFormat

@BindingAdapter("goodsPrice")
fun TextView.setPrice(price: Int) {
    text = context.getString(R.string.text_price, DecimalFormat("#,###").format(price))
}

@BindingAdapter("goodsImage")
fun ImageView.setImage(imageUrl: String) {
    Glide.with(this.context).load(imageUrl).into(this)
}

@BindingAdapter("goodsList")
fun RecyclerView.bindGoods(
    items: List<GoodsUiModel>?,
) {
    (adapter as? GoodsAdapter)?.updateItems(items.orEmpty())
}
