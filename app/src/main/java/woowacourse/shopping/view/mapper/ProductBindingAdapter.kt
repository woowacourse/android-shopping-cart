package woowacourse.shopping.view.mapper

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.SingleRequest
import com.bumptech.glide.request.target.Target
import woowacourse.shopping.R

@BindingAdapter("android:price")
fun setPrice(
    view: TextView,
    price: Int,
) {
    view.text = view.context.getString(R.string.template_price, price)
}

@BindingAdapter("android:image")
fun setImage(
    view: ImageView,
    url: String,
) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.placeholder_product)
        .into(view)

}
