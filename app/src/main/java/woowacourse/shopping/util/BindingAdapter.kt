package woowacourse.shopping.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R
import java.text.DecimalFormat

@BindingAdapter("goodsPrice")
fun TextView.setPrice(price: Int) {
    text = context.getString(R.string.text_price, DecimalFormat("#,###").format(price))
}

@BindingAdapter("goodsImage")
fun ImageView.setImage(imageId: Int) {
    setImageResource(imageId)
}
