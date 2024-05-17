package woowacourse.shopping.view.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.CART_ITEM_LOAD_PAGING_SIZE
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.CART_ITEM_PAGE_SIZE
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.MIN_PAGE_COUNT
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.view.ViewModelFactory
import woowacourse.shopping.view.cart.adapter.ShoppingCartAdapter
import woowacourse.shopping.view.detail.ProductDetailFragment

class ShoppingCartFragment : Fragment(), OnClickShoppingCart {
    private var _binding: FragmentShoppingCartBinding? = null
    val binding: FragmentShoppingCartBinding get() = _binding!!

    private val shoppingCartViewModel: ShoppingCartViewModel by lazy {
        val viewModelFactory =
            ViewModelFactory { ShoppingCartViewModel(ShoppingCartRepositoryImpl(context = requireContext())) }
        viewModelFactory.create(ShoppingCartViewModel::class.java)
    }
    private lateinit var adapter: ShoppingCartAdapter

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
        initView()
        observeData()
    }

    private fun initView() {
        shoppingCartViewModel.loadPagingCartItemList(CART_ITEM_LOAD_PAGING_SIZE)
        binding.onClickShoppingCart = this
        binding.currentPage = shoppingCartViewModel.currentPage
        adapter =
            ShoppingCartAdapter(
                onClickShoppingCart = this,
                loadLastItem = {
                    shoppingCartViewModel.loadPagingCartItemList(CART_ITEM_LOAD_PAGING_SIZE)
                },
            )
        binding.rvShoppingCart.adapter = adapter
    }

    private fun observeData() {
        shoppingCartViewModel.shoppingCart.cartItems.observe(viewLifecycleOwner) {
            updateRecyclerView()
        }
        shoppingCartViewModel.shoppingCartState.observe(viewLifecycleOwner) { shoppingCartState ->
            when (shoppingCartState) {
                ShoppingCartState.Init -> {}
                ShoppingCartState.DeleteShoppingCart.Success -> {}
                ShoppingCartState.DeleteShoppingCart.Fail -> showMessage(ERROR_DELETE_FAIL_MESSAGE)
                ShoppingCartState.LoadCartItemList.Success -> {}
                ShoppingCartState.LoadCartItemList.Fail -> showMessage(MAX_PAGING_DATA_MESSAGE)
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
        shoppingCartViewModel.deleteShoppingCartItem(cartItemId)
    }

    override fun clickPrevPage() {
        if (isExistPrevPage()) {
            binding.currentPage = --shoppingCartViewModel.currentPage
            updateRecyclerView()
        }
    }

    override fun clickNextPage() {
        if (isExistNextPage()) {
            binding.currentPage = ++shoppingCartViewModel.currentPage
            updateRecyclerView()
        } else {
            showMessage(MAX_PAGING_DATA_MESSAGE)
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
        val startIndex = (shoppingCartViewModel.currentPage - MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE
        val endIndex = minOf(
            shoppingCartViewModel.currentPage * CART_ITEM_PAGE_SIZE,
            shoppingCartViewModel.totalItemSize
        )
        val newData =
            shoppingCartViewModel.shoppingCart.cartItems.value?.subList(startIndex, endIndex)
                ?: emptyList()
        adapter.updateCartItems(hasLastItem(endIndex), newData)
        updateImageButtonColor()
    }

    private fun hasLastItem(endIndex: Int): Boolean {
        return endIndex >= (shoppingCartViewModel.totalItemSize)
    }

    private fun isExistPrevPage(): Boolean {
        return shoppingCartViewModel.currentPage > MIN_PAGE_COUNT
    }

    private fun isExistNextPage(): Boolean {
        return shoppingCartViewModel.currentPage * CART_ITEM_PAGE_SIZE < shoppingCartViewModel.totalItemSize
    }

    private fun updateImageButtonColor() {
        binding.onPrevButton = isExistPrevPage()
        binding.onNextButton = isExistNextPage()
    }

    private fun showMessage(message: String) =
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

    companion object {
        private const val MAX_PAGING_DATA_MESSAGE = "모든 데이터가 로드 되었습니다."
        private const val ERROR_DELETE_FAIL_MESSAGE = "삭제에 실패하였습니다."
    }
}
