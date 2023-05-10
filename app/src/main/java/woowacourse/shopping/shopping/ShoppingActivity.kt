package woowacourse.shopping.shopping

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        setSupportActionBar(findViewById(R.id.shopping_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_shopping, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
