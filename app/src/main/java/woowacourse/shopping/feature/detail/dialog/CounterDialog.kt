package woowacourse.shopping.feature.detail.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.R
import woowacourse.shopping.common_ui.CounterView
import woowacourse.shopping.data.repository.local.CartRepositoryImpl
import woowacourse.shopping.data.sql.cart.CartDao
import woowacourse.shopping.databinding.DialogCounterBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.getParcelableCompat

class CounterDialog : DialogFragment(), CounterDialogContract.View {
    private var _binding: DialogCounterBinding? = null
    private val binding: DialogCounterBinding
        get() = _binding!!

    private lateinit var presenter: CounterDialogContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product =
            arguments?.getParcelableCompat<ProductUiModel>(PRODUCT_KEY) ?: return dismiss()
        val count =
            savedInstanceState?.getInt(COUNT_RESTORE_KEY)

        setInitPresenter(product, count)
        binding.presenter = presenter
        binding.product = product

        binding.counterView.countStateChangeListener =
            object : CounterView.OnCountStateChangeListener {
                override fun onCountChanged(counterNavigationView: CounterView?, count: Int) {
                    presenter.changeCount(count)
                }
            }
    }

    private fun setInitPresenter(product: ProductUiModel, count: Int?) {
        presenter = CounterDialogPresenter(
            this,
            CartRepositoryImpl(CartDao(requireContext())),
            product,
            count
        )
        presenter.initPresenter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT_RESTORE_KEY, presenter.changeCount)
    }

    override fun setCountState(count: Int) {
        binding.counterView.setCountState(count)
    }

    override fun notifyChangeApplyCount(changeApplyCount: Int) {
        parentFragmentManager.setFragmentResult(
            CHANGE_COUNTER_APPLY_KEY,
            bundleOf(COUNT_KEY to changeApplyCount)
        )
        Toast.makeText(requireContext(), getString(R.string.success_add_cart), Toast.LENGTH_SHORT).show()
    }

    override fun exit() {
        dismiss()
    }

    companion object {
        private const val PRODUCT_KEY = "product_key"
        private const val COUNT_RESTORE_KEY = "count_restore_key"

        const val CHANGE_COUNTER_APPLY_KEY = "change_counter_apply_key"
        const val COUNT_KEY = "change_count_key"

        @JvmStatic
        fun newInstance(product: ProductUiModel): CounterDialog {
            return CounterDialog().apply {
                arguments = bundleOf(
                    PRODUCT_KEY to product.copy()
                )
            }
        }
    }
}
