package woowacourse.shopping.view.core.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("setImage")
fun ImageView.imageBindingAdapter(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.landscape_placeholder)
            .error(R.drawable.no_image)
            .into(this)
    } else {
        setImageResource(R.drawable.landscape_placeholder)
    }
}
