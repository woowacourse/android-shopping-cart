package woowacourse.shopping.presentation.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ViewBinding {

    @JvmStatic
    @BindingAdapter("imageUrl", "error")
    fun loadImage(view: ImageView, url: String?, error: Drawable?) {
        Glide.with(view.context)
            .load(url)
            .error(error)
            .centerCrop()
            .into(view)
    }
}
