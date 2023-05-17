package woowacourse.shopping

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CustomBindingAdapter {

    @BindingAdapter("imgResId")
    @JvmStatic
    fun setImageResource(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(view)
    }
}
