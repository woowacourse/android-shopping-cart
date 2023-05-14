package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.ProductModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    private lateinit var adpater: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        presenter = CartPresenter(this, CartDbRepository(this), ProductMockRepository)
        presenter.fetchProducts()
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"
    }

    override fun showProducts(cartProducts: List<ProductModel>, isExistUndo: Boolean, isExistNext: Boolean, count: String) {
        adpater = CartAdapter(
            cartProducts,
            object : CartAdapter.OnItemClick {
                override fun onRemoveClick(id: Int) {
                    presenter.removeProduct(id)
                }

                override fun onNextClick() {
                    presenter.fetchNextPage()
                }

                override fun onUndoClick() {
                    presenter.fetchUndoPage()
                }
            },
            isExistUndo,
            isExistNext,
            count,
        )
        binding.recyclerCart.adapter = adpater
    }

    override fun showOtherPage(size: Int) {
        adpater.updateCartItems(size)
    }

    override fun notifyRemoveItem(position: Int) {
        adpater.removeCartItems(position)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.handleNextStep(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun handleBackButtonClicked() {
        finish()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
