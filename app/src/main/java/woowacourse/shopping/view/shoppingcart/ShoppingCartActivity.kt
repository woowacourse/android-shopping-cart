package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.db.CartProductDao
import woowacourse.shopping.data.repository.CartProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.model.uimodel.CartProductUIModel
import woowacourse.shopping.model.uimodel.ProductUIModel

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {
    override lateinit var presenter: ShoppingCartContract.Presenter
    private lateinit var adapter: ShoppingCartAdapter

    private var _binding: ActivityShoppingCartBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)

        setToolBar()
        setAdapter()
        setPresenter()
        setViewSettings()
        setPageMoveClick()
    }

    private fun setToolBar() {
        setSupportActionBar(binding.tbCart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setPresenter() {
        presenter = ShoppingCartPresenter(
            view = this,
            cartProductRepository = CartProductRepositoryImpl(CartProductDao(this))
        )
    }

    private fun setAdapter() {
        adapter = ShoppingCartAdapter(emptyList(), setOnClickRemove())
    }

    private fun setViewSettings() {
        binding.rvCartList.adapter = adapter
        updatePageCounter(INIT_PAGE_COUNTER_VIEW)
    }

    private fun setPageMoveClick() {
        binding.tvPageUp.setOnClickListener {
            presenter.loadNextPage(it.isActivated)
        }
        binding.tvPageDown.setOnClickListener {
            presenter.loadPreviousPage(it.isActivated)
        }
    }

    private fun setOnClickRemove(): (ProductUIModel) -> Unit = { product ->
        presenter.removeCartProduct(product)
    }

    override fun updateCartProduct(cartProducts: List<CartProductUIModel>) {
        adapter.update(cartProducts)
    }

    override fun activatePageUpCounter() {
        binding.tvPageUp.isActivated = true
    }

    override fun deactivatePageUpCounter() {
        binding.tvPageUp.isActivated = false
    }

    override fun activatePageDownCounter() {
        binding.tvPageDown.isActivated = true
    }

    override fun deactivatePageDownCounter() {
        binding.tvPageDown.isActivated = false
    }

    override fun updatePageCounter(count: Int) {
        binding.tvPageCounter.text = count.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun intent(context: Context) = Intent(context, ShoppingCartActivity::class.java)

        private const val INIT_PAGE_COUNTER_VIEW = 1
    }
}
