package woowacourse.shopping.presentation.ui.shoppingCart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.model.Operator
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.adapter.ShoppingCartAdapter
import woowacourse.shopping.presentation.ui.shoppingCart.adapter.ShoppingCartClickListener

class ShoppingCartActivity :
    AppCompatActivity(),
    ShoppingCartContract.View,
    ShoppingCartClickListener {
    private lateinit var binding: ActivityShoppingCartBinding
    override val presenter: ShoppingCartContract.Presenter by lazy { initPresenter() }
    private val shoppingCartAdapter = ShoppingCartAdapter(this)

    override fun checkItem(position: Int, isChecked: Boolean) {
        presenter.changeSelection(position, isChecked)
    }

    override fun clickChangeQuality(position: Int, op: Operator) {
        presenter.updateProductQuantity(position, op)
    }

    override fun clickDelete(position: Int) {
        presenter.deleteProductInCart(position)
    }

    override fun clickItem(position: Int) {
        presenter.showProductDetail(position)
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
        initClickListeners()
        binding.listShoppingCart.adapter = shoppingCartAdapter
    }

    private fun initView() {
        presenter.getShoppingCart()
        presenter.setPageNumber()
        presenter.checkPageMovement()
        presenter.setOrderCount()
        presenter.setPayment()
        presenter.setAllCheck()
    }

    private fun initClickListeners() {
        clickNextPage()
        clickPreviousPage()
        checkAllProduct()
        clickBackButton()
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

    override fun goProductDetailActivity(productInCart: ProductInCart) {
        val intent = ProductDetailActivity.getIntent(this, productInCart.product.id)
        startActivity(intent)
    }

    override fun updateAllCheck(isChecked: Boolean) {
        binding.checkShoppingCartAll.isChecked = isChecked
    }

    override fun updateOrder(orderCount: Int) {
        binding.textShoppingCartOrder.text = getString(R.string.orderCount, orderCount)
    }

    override fun updatePayment(payment: Int) {
        binding.textShoppingCartPayment.text = getString(R.string.detailPriceFormat, payment)
    }

    override fun showUnExpectedError() {
        Toast.makeText(this, getString(R.string.unexpected_error), Toast.LENGTH_SHORT).show()
    }

    private fun checkAllProduct() {
        binding.checkShoppingCartAll.setOnClickListener {
            presenter.selectAll((it as CheckBox).isChecked)
        }
    }

    private fun clickBackButton() {
        binding.buttonShoppingCartBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
