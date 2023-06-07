package woowacourse.shopping.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import woowacourse.shopping.R

@BindingAdapter("ImageBinding")
fun setNoticeImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .error(R.drawable.ic_launcher_foreground)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}
