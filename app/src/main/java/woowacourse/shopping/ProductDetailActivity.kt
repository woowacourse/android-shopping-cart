package woowacourse.shopping

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setSupportActionBar(findViewById(R.id.product_detail_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
