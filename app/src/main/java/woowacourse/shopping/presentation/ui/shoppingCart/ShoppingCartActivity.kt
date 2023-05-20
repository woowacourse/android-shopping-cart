package woowacourse.shopping.presentation.ui.shoppingCart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.common.uimodel.Operator
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.adapter.ShoppingCartAdapter
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartContract
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartPresenter

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {
    private lateinit var binding: ActivityShoppingCartBinding
    override val presenter: ShoppingCartContract.Presenter by lazy { initPresenter() }
    private val shoppingCartAdapter = ShoppingCartAdapter(setUpClickListener())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initClickListeners()
        binding.rvShoppingCart.adapter = shoppingCartAdapter
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

        override fun setClickEventOnOperatorButton(operator: Boolean, productInCart: Product) {
            val request = if (operator) Operator.PLUS else Operator.MINUS
            presenter.addCountOfProductInCart(request, productInCart)
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

    private fun initPresenter(): ShoppingCartPresenter {
        return ShoppingCartPresenter(
            this,
            ShoppingCartRepositoryImpl(
                shoppingCartDataSource = ShoppingCartDao(this),
                productDataSource = ProductDao(this),
            ),
        )
    }

    companion object {
        private const val INIT_PAGE = 1

        fun getIntent(context: Context): Intent = Intent(context, ShoppingCartActivity::class.java)
    }
}
