package woowacourse.shopping

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
                title = it.name,
                money = it.price,
            )
        }
    }
}
