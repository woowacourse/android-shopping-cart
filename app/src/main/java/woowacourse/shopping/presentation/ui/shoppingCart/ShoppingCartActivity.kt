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

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {
    private lateinit var binding: ActivityShoppingCartBinding
    override val presenter: ShoppingCartContract.Presenter by lazy { initPresenter() }
    private val shoppingCartAdapter = ShoppingCartAdapter(
        { clickItem(it) },
        { presenter.deleteProductInCart(it) },
    )

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

        initView()
        initClickListeners()
        binding.listShoppingCart.adapter = shoppingCartAdapter
    }

    private fun initView() {
        presenter.getShoppingCart()
        presenter.setPageNumber()
        presenter.checkPageMovement()
    }

    private fun initClickListeners() {
        clickNextPage()
        clickPreviousPage()
    }

    override fun setShoppingCart(shoppingCart: List<ProductInCart>) {
        shoppingCartAdapter.submitList(shoppingCart)
    }

    override fun deleteProduct(index: Int) {
        shoppingCartAdapter.notifyItemRemoved(index)
    }

    override fun setPage(pageNumber: Int) {
        binding.textShoppingCartPageNumber.text = pageNumber.toString()
    }

    override fun setPageButtonEnable(previous: Boolean, next: Boolean) {
        binding.buttonShoppingCartNextPage.isEnabled = next
        binding.buttonShoppingCartPreviousPage.isEnabled = previous
    }

    private fun clickNextPage() {
        binding.buttonShoppingCartNextPage.setOnClickListener {
            presenter.goNextPage()
        }
    }

    private fun clickPreviousPage() {
        binding.buttonShoppingCartPreviousPage.setOnClickListener {
            presenter.goPreviousPage()
        }
    }

    private fun clickItem(product: ProductInCart) {
        val intent = ProductDetailActivity.getIntent(this, product.product.id)
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
