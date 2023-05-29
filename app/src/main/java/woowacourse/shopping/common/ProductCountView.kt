package woowacourse.shopping.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.databinding.LayoutMainCountBinding

class ProductCountView @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributesSet, defStyleAttr) {

    val binding = LayoutMainCountBinding.inflate(
        LayoutInflater.from(context),
        this,
        true,
    )

    var count: Int
        get() = binding.countView.count
        set(value) {
            binding.countView.count = value
        }

    var plusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.countView.plusClickListener = {
                value?.invoke()
            }
            binding.buttonAddProduct.setOnClickListener {
                value?.invoke()
                count = INITIAL_AMOUNT
                updateBtnState(INITIAL_AMOUNT)
            }
        }

    var minusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.countView.minusClickListener = {
                value?.invoke()
                if (count == INITIAL_AMOUNT) {
                    showAddBtn()
                }
            }
        }

    fun updateBtnState(count: Int) {
        if (count < INITIAL_AMOUNT) {
            showAddBtn()
        } else {
            hideAddBtn()
            this.count = count
        }
    }

    private fun showAddBtn() {
        binding.countView.visibility = GONE
        binding.buttonAddProduct.visibility = VISIBLE
    }

    private fun hideAddBtn() {
        binding.countView.visibility = VISIBLE
        binding.buttonAddProduct.visibility = GONE
    }

    companion object {
        private const val INITIAL_AMOUNT = 1
    }
}
