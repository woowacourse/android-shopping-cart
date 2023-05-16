package woowacourse.shopping.presentation.ui.shoppingCart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.adapter.ShoppingCartAdapter
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartContract
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartPresenter

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {
    private lateinit var binding: ActivityShoppingCartBinding
    override val presenter: ShoppingCartContract.Presenter by lazy { initPresenter() }
    private val shoppingCartAdapter = ShoppingCartAdapter(::clickItem, ::clickItemDelete)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initClickListeners()
        binding.listShoppingCart.adapter = shoppingCartAdapter
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

    override fun setShoppingCart(shoppingCart: List<ProductInCart>) {
        shoppingCartAdapter.initProducts(shoppingCart)
    }

    override fun setPage(pageNumber: Int) {
        binding.textShoppingCartPageNumber.text = pageNumber.toString()
    }

    override fun clickNextPage() {
        binding.buttonShoppingCartNextPage.setOnClickListener {
            presenter.goNextPage()
        }
    }

    override fun clickPreviousPage() {
        binding.buttonShoppingCartPreviousPage.setOnClickListener {
            presenter.goPreviousPage()
        }
    }

    override fun setPageButtonEnable(previous: Boolean, next: Boolean) {
        binding.buttonShoppingCartNextPage.isEnabled = next
        binding.buttonShoppingCartPreviousPage.isEnabled = previous
    }

    private fun clickItem(productInCart: ProductInCart) {
        val intent = ProductDetailActivity.getIntent(this, productInCart.product.id)
        startActivity(intent)
    }

    private fun clickItemDelete(productInCart: ProductInCart): Boolean {
        return presenter.deleteProductInCart(productInCart.product.id)
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
        fun getIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
