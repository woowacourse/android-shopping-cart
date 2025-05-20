package woowacourse.shopping.presentation.view.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentDetailBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.view.detail.event.DetailMessageEvent

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val viewModel: DetailViewModel by viewModels { DetailViewModel.Factory }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initListener()

        val productId = arguments?.getLong(EXTRA_PRODUCT) ?: return
        viewModel.fetchProduct(productId)
    }

    private fun initObserver() {
        binding.vm = viewModel

        viewModel.addToCartSuccessEvent.observe(viewLifecycleOwner) {
            parentFragmentManager.popBackStack()
        }

        viewModel.toastEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                DetailMessageEvent.FETCH_PRODUCT_FAILURE -> showToast(R.string.detail_screen_event_message_fetch_product_failure)
                DetailMessageEvent.ADD_PRODUCT_FAILURE -> showToast(R.string.detail_screen_event_message_add_product_failure)
            }
        }
    }

    private fun initListener() {
        binding.btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        private const val EXTRA_PRODUCT = "product"

        fun newBundle(productId: Long) =
            bundleOf(
                EXTRA_PRODUCT to productId,
            )
    }
}
