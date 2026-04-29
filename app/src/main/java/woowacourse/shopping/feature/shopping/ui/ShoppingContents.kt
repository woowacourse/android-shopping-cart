package woowacourse.shopping.feature.shopping.ui

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.model.Product
import woowacourse.shopping.feature.detail.DetailActivity
import woowacourse.shopping.feature.shopping.ShoppingScreen

@Composable
fun ShoppingContents(
    products: ImmutableList<Product>,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(20.dp),
    ) {
        items(
            items = products,
            key = { product -> product.id },
        ) { product ->
            ProductCard(
                onClick = {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("id", product.id)
                    activity?.startActivity(intent)
                },
                imageUrl = product.imageUrl,
                productName = product.name,
                price = product.price,
            )
        }
    }
}

@Preview
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen(
        data = emptyList<Product>().toImmutableList(),
    )
}
