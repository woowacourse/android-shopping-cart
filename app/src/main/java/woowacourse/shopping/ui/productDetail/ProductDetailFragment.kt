package woowacourse.shopping.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.NonePagingStrategy
import woowacourse.shopping.R
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.repository.DummyShoppingProductsRepository
import woowacourse.shopping.source.DummyProductIdsCountDataSource

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private lateinit var factory: UniversalViewModelFactory
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater)

        initViewModel()

        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.onItemChargeListener = viewModel

        return binding.root
    }

    private fun initViewModel() {
        arguments?.let {
            factory =
                UniversalViewModelFactory {
                    ProductDetailViewModel(
                        productId = it.getInt(PRODUCT_ID),
                        shoppingProductsRepository = DummyShoppingProductsRepository(NonePagingStrategy()),
                        productIdsCountRepository = DefaultProductIdsCountRepository(DummyProductIdsCountDataSource()),
                    )
                }
            viewModel = ViewModelProvider(this, factory)[ProductDetailViewModel::class.java]
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.productDetailToolbar.setOnMenuItemClickListener {
            navigateToMenuItem(it)
        }
    }

    private fun navigateToMenuItem(it: MenuItem) =
        when (it.itemId) {
            R.id.action_x -> navigateToPreviousFragment()

            else -> false
        }

    private fun navigateToPreviousFragment(): Boolean {
        parentFragmentManager.popBackStack()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PRODUCT_ID = "productId"

        fun newInstance(productId: Int): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle().apply { putInt(PRODUCT_ID, productId) }
            fragment.arguments = bundle
            return fragment
        }
    }
}
