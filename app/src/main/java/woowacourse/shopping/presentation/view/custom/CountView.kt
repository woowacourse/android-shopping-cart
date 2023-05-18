package woowacourse.shopping.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutCountViewBinding

class CountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), CountContract.View {
    private val binding = LayoutCountViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val presenter = CountPresenter(this)

    var countStateChangeListener: OnCountStateChangeListener? = null

    init {
        presenter.initCount()
    }

    override fun setMinusButton() {
        binding.btCountMinus.setOnClickListener {
            presenter.updateMinusCount()
            countStateChangeListener?.onCountChanged(this, presenter.getCount())
        }
    }

    override fun setPlusButton() {
        binding.btCountPlus.setOnClickListener {
            presenter.updatePlusCount()
            countStateChangeListener?.onCountChanged(this, presenter.getCount())
        }
    }

    override fun setCountTextView(count: Int) {
        binding.tvCount.text = count.toString()
    }

    override fun updateCount(count: Int) {
        presenter.updateCount(count)
    }

    fun setMinCount(minCount: Int) {
        presenter.minCountValue = minCount
    }

    fun setMaxCount(maxCount: Int) {
        presenter.maxCountValue = maxCount
    }

    override fun getCount(): Int = presenter.getCount()
    interface OnCountStateChangeListener {
        fun onCountChanged(countView: CountView?, count: Int)
    }
}
