package woowacourse.shopping.utils.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutCounterBinding
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

class Counter(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    val binding = LayoutCounterBinding.inflate(LayoutInflater.from(context), this, true)

    var minCount = 1
    var maxCount = 100

    var count: Int by object : ObservableProperty<Int>(1) {
        override fun beforeChange(property: KProperty<*>, oldValue: Int, newValue: Int): Boolean {
            return count in minCount..maxCount
        }
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            return binding.tvCount.text.toString().toInt()
        }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            binding.tvCount.text = value.toString()
        }
    }

    val tvPlus = binding.tvPlus
    val tvMinus = binding.tvMinus

    init {
        binding.tvCount.text = "1"
    }
}