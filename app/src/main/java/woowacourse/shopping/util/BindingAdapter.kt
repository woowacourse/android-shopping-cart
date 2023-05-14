package woowacourse.shopping.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import woowacourse.shopping.R

@BindingAdapter("bind:imageUrl")
fun ImageView.setImageUrl(imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.ic_launcher_foreground)
        .fallback(R.drawable.ic_launcher_foreground)
        .into(this)
}
