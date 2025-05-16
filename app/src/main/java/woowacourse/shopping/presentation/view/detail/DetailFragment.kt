package woowacourse.shopping.presentation.view.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentDetailBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.view.cart.CartFragment

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

        viewModel.saveState.observe(viewLifecycleOwner) {
            it?.let { navigateToScreen() }
        }
    }

    private fun initListener() {
        binding.btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun navigateToScreen() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.shopping_fragment_container, CartFragment::class.java, null)
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
