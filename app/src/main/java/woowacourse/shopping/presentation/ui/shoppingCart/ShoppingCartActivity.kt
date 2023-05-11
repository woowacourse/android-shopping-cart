package woowacourse.shopping.presentation.ui.shoppingCart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.presentation.ui.shoppingCart.adapter.ShoppingCartAdapter
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartContract
import woowacourse.shopping.presentation.ui.shoppingCart.presenter.ShoppingCartPresenter

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {
    private lateinit var binding: ActivityShoppingCartBinding
    override val presenter: ShoppingCartContract.Presenter by lazy { initPresenter() }
    private val shoppingCartAdapter = ShoppingCartAdapter(::setClickEventOnProduct)

    override fun setShoppingCart(shoppingCart: List<ProductInCart>) {
        shoppingCartAdapter.initProducts(shoppingCart)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        binding.rvShoppingCart.adapter = shoppingCartAdapter
    }

    private fun setClickEventOnProduct(position: Int) {
    }

    private fun initView() {
        presenter.getShoppingCart(INIT_PAGE)
    }

    companion object {
        private const val INIT_PAGE = 1
    }
}
