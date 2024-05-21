package woowacourse.shopping.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CommonBindingAdapters {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageResource(
        view: ImageView,
        imageUrl: String,
    ) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }

    @BindingAdapter("setVisibleByCondition")
    @JvmStatic
    fun setVisibleByCondition(
        view: ImageView,
        condition: Boolean,
    ) {
        if (condition)
            {
                view.visibility = View.VISIBLE
            } else {
            view.visibility = View.INVISIBLE
        }
    }
}
