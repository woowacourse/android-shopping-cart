package woowacourse.shopping.presentation.binding

import android.graphics.drawable.Drawable
import android.view.View
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

    @JvmStatic
    @BindingAdapter("setGoneIfZero")
    fun setGoneIfZero(view: View, condition: Int) {
        if (condition == 0) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("setGoneIfNotZero")
    fun setGoneIfNotZero(view: View, condition: Int) {
        if (condition == 0) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}
