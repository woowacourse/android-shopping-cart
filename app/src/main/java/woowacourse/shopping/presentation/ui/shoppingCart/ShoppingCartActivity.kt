package woowacourse.shopping.presentation.ui.shoppingCart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.adapter.ShoppingCartAdapter
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartContract
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartPresenter

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {
    private lateinit var binding: ActivityShoppingCartBinding
    override val presenter: ShoppingCartPresenter by lazy { initPresenter() }
    private val shoppingCartAdapter = ShoppingCartAdapter(setUpClickListener())

    private fun initPresenter(): ShoppingCartPresenter {
        return ShoppingCartPresenter(
            this,
            ShoppingCartRepositoryImpl(
                shoppingCartDataSource = ShoppingCartDao(this),
                productDataSource = ProductDao(this),
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPresenterDataBinding()
        initView()
        initClickListeners()
        binding.rvShoppingCart.adapter = shoppingCartAdapter
    }

    private fun initPresenterDataBinding() {
        binding.presenter = presenter
        binding.lifecycleOwner = this
    }

    private fun initView() {
        presenter.getShoppingCart(INIT_PAGE)
        presenter.setPageNumber()
        presenter.checkPageMovement()
    }

    private fun initClickListeners() {
        clickNextPage()
        clickPreviousPage()
    }

    override fun setShoppingCart(shoppingCart: List<ProductInCartUiState>) {
        shoppingCartAdapter.initProducts(shoppingCart)
    }

    override fun setPage(pageNumber: Int) {
        binding.tvShoppingCartPageNumber.text = pageNumber.toString()
    }

    override fun clickNextPage() {
        binding.ivShoppingCartNextPage.setOnClickListener {
            presenter.goNextPage()
        }
    }

    override fun clickPreviousPage() {
        binding.ivShoppingCartPreviousButton.setOnClickListener {
            presenter.goPreviousPage()
        }
    }

    override fun setPageButtonEnable(previous: Boolean, next: Boolean) {
        binding.ivShoppingCartNextPage.isEnabled = next
        binding.ivShoppingCartPreviousButton.isEnabled = previous
    }

    private fun setUpClickListener() = object : ShoppingCartSetClickListener {
        override fun setClickEventOnItem(productInCart: ProductInCartUiState) {
            setEventOnItem(productInCart)
        }

        override fun setClickEventOnDeleteButton(productInCart: ProductInCartUiState) {
            setEventOnDelete(productInCart)
        }

        override fun setClickEventOnOperatorButton(
            operator: Boolean,
            productInCart: ProductInCartUiState,
        ) {
            val request = if (operator) Operator.PLUS else Operator.MINUS
            if (productInCart.quantity == 1 && !operator) {
                setEventOnDelete(productInCart)
                return
            }

            presenter.addCountOfProductInCart(request, productInCart)
        }

        override fun setClickEventOnCheckBox(
            isChecked: Boolean,
            productInCart: ProductInCartUiState,
        ) {
            presenter.calculateTotalWithCheck(isChecked, productInCart)
        }
    }

    private fun setEventOnItem(productInCart: ProductInCartUiState) {
        val intent = ProductDetailActivity.getIntent(this, productInCart.product.id)
        startActivity(intent)
    }

    private fun setEventOnDelete(productInCart: ProductInCartUiState) {
        val result = presenter.deleteProductInCart(productInCart.product.id)
        if (result) shoppingCartAdapter.deleteItem(productInCart.product.id)
    }

    companion object {
        private const val INIT_PAGE = 1
        fun getIntent(context: Context): Intent = Intent(context, ShoppingCartActivity::class.java)
    }
}
