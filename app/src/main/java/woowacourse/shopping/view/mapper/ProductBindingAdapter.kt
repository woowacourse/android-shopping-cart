package woowacourse.shopping.view.mapper

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("android:price")
fun setPrice(
    view: TextView,
    price: Int,
) {
    view.text = view.context.getString(R.string.template_price, price)
}

@BindingAdapter("shoppingCart:image")
fun setImage(
    view: ImageView,
    url: String,
) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.placeholder_product)
        .into(view)
}

@BindingAdapter("shoppingCart:mutableVisibility")
fun setMutableVisibility(
    view: ViewGroup,
    quantity: MutableLiveData<Int>,
) {
    view.findViewTreeLifecycleOwner()?.let {
        quantity.observe(it) {
            val button = view.findViewById<Button>(R.id.btn_item_product_add_to_cart)
            val quantitySelector = view.findViewById<ViewGroup>(R.id.layout_product_quantity_selector)

            button.visibility = if (quantity.value!! < 1) View.VISIBLE else View.GONE
            quantitySelector.visibility = if (quantity.value!! < 1) View.GONE else View.VISIBLE
        }
    }
}

@BindingAdapter("shoppingCart:quantity")
fun setQuantity(
    view: TextView,
    quantity: MutableLiveData<Int>,
) {
    view.findViewTreeLifecycleOwner()?.let {
        quantity.observe(it) {
            view.text = quantity.value.toString()
        }
    }
}
