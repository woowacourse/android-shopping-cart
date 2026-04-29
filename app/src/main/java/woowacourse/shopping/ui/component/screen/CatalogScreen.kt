package woowacourse.shopping.ui.component.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.component.frame.CommonFrame
import woowacourse.shopping.ui.component.item.ShoppingItem

@Composable
fun CatalogScreen(catalog: List<Product>, modifier: Modifier = Modifier) {
    CommonFrame(
        headerContent = { CatalogHeader() },
        bodyContent = { CatalogBody(catalog) },
        modifier = modifier,
    )
}

@Composable
private fun CatalogHeader(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "Shopping",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Icon(
            painter = painterResource(R.drawable.ic_cart),
            contentDescription = "장바구니 아이콘",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun CatalogBody(
    catalog: List<Product>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
    ) {
        items(catalog.size) { item ->
            ShoppingItem(catalog[item])
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CatalogScreenPreview() {
    val catalog = listOf(
        Product(
            imageUri = "hello",
            name = "너무너무너무긴아이템이름",
            price = 100000
        ),
        Product(
            imageUri = "디디",
            name = "당근주스",
            price = 1000
        ),
        Product(
            imageUri = "hello",
            name = "우유",
            price = 100
        ),
        Product(
            imageUri = "hello",
            name = "투핸더",
            price = 100000000
        )

    )

    CatalogScreen(catalog)
}
