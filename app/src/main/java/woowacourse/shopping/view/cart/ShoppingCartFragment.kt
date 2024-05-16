package woowacourse.shopping.view.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.cart.adapter.ShoppingCartAdapter
import woowacourse.shopping.view.detail.ProductDetailFragment
import woowacourse.shopping.view.viewmodel.MainViewModel

class ShoppingCartFragment : Fragment(), OnClickShoppingCart {
    private var _binding: FragmentShoppingCartBinding? = null
    val binding: FragmentShoppingCartBinding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ShoppingCartAdapter
    private var currentPage = MIN_PAGE_COUNT
    private var totalItemSize = DEFAULT_ITEM_SIZE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (requireActivity() as MainActivity).viewModel
        initView()
        observeData()
    }

    private fun initView() {
        mainViewModel.loadPagingCartItem(CART_ITEM_LOAD_PAGING_SIZE)
        binding.onClickShoppingCart = this
        binding.currentPage = currentPage
        adapter =
            ShoppingCartAdapter(
                onClickShoppingCart = this,
                loadLastItem = {
                    mainViewModel.loadPagingCartItem(CART_ITEM_LOAD_PAGING_SIZE)
                },
            )
        binding.rvShoppingCart.adapter = adapter
    }

    private fun observeData() {
        mainViewModel.shoppingCart.cartItems.observe(viewLifecycleOwner) { cartItems ->
            totalItemSize = cartItems?.size ?: DEFAULT_ITEM_SIZE
            view?.post {
                updateRecyclerView()
            }
        }
    }

    override fun clickBack() {
        parentFragmentManager.popBackStack()
    }

    override fun clickCartItem(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        changeFragment(productFragment)
    }

    override fun clickRemoveCartItem(cartItemId: Long) {
        mainViewModel.deleteShoppingCartItem(cartItemId)
    }

    override fun clickPrevPage() {
        if (currentPage > MIN_PAGE_COUNT) {
            binding.currentPage = --currentPage
            updateRecyclerView()
        }
    }

    override fun clickNextPage() {
        if (currentPage * CART_ITEM_PAGE_SIZE < totalItemSize) {
            binding.currentPage = ++currentPage
            updateRecyclerView()
        }
    }

    private fun changeFragment(nextFragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateRecyclerView() {
        val startIndex = (currentPage - MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE
        val endIndex = minOf(currentPage * CART_ITEM_PAGE_SIZE, totalItemSize)
        val newData =
            mainViewModel.shoppingCart.cartItems.value?.subList(startIndex, endIndex) ?: emptyList()
        adapter.updateCartItems(hasLastItem(endIndex), newData)
    }

    private fun hasLastItem(endIndex: Int): Boolean {
        return endIndex >= (mainViewModel.shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE)
    }

    companion object {
        private const val CART_ITEM_LOAD_PAGING_SIZE = 5
        const val CART_ITEM_PAGE_SIZE = 3
        private const val MIN_PAGE_COUNT = 1
        const val DEFAULT_ITEM_SIZE = 0
    }
}
