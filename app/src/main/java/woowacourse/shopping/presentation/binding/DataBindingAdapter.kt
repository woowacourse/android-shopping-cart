package woowacourse.shopping.presentation.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.presentation.common.CounterView

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl", "error")
    fun loadImage(view: ImageView, url: String?, error: Drawable?) {
        Glide.with(view.context)
            .load(url)
            .error(error)
            .centerCrop()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("counterText")
    fun setCounterText(view: CounterView, count: Int) {
        view.setCountText(count)
    }
}
