package woowacourse.shopping.presentation.view.productlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.productlist.adpater.ProductListAdapter

class ProductListActivity : AppCompatActivity(), ProductContract.View {
    private lateinit var binding: ActivityProductListBinding

    private val presenter: ProductContract.Presenter by lazy {
        ProductListPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)

        presenter.loadProductItems()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun setProductItems(products: List<ProductModel>) {
        binding.rvProductList.adapter = ProductListAdapter(products, ::moveToActivity)
    }

    private fun moveToActivity(product: ProductModel) {
        TODO("다음 화면 인텐트 받아오기")
        val intent = Intent(Intent.ACTION_VIEW)
        startActivity(intent)
    }
}
