package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.state.ProductUiModel

@Composable
fun ProductList(
    products: List<ProductUiModel>,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        items(
            key = { it.id },
            items = products,
        ) {
            SingleProductItem(
                imageUrl = it.imageUrl,
                title = it.title,
                price = it.price,
                modifier = Modifier.clickable(
                    onClick = {
                        onProductClick(it.id)
                    },
                ),
            )
        }
    }
}
