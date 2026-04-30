package woowacourse.shopping.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import java.util.UUID

@Composable
fun CatalogScreen(
    catalog: List<Product>,
    onItemClick: (UUID) -> Unit,
    onCartClick: () -> Unit,
    onLoadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonFrame(
        headerContent = { CatalogHeader(onCartClick) },
        bodyContent = {
            CatalogBody(
                catalog, onItemClick,
                onLoadClick = onLoadClick
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun CatalogHeader(
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onCartClick)
        )
    }
}

@Composable
private fun CatalogBody(
    catalog: List<Product>,
    onItemClick: (UUID) -> Unit,
    onLoadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
    ) {
        items(catalog.size) { item ->
            ShoppingItem(
                product = catalog[item],
                onClick = onItemClick
            )
        }

        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            LoadBtn(onLoadClick)
        }
    }
}

@Composable
private fun LoadBtn(
    onLoad: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(R.drawable.ic_add),
        contentDescription = "더보기 버튼",
        tint = Color.White,
        modifier = modifier
            .padding(25.dp)
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.LightGray)
            .clickable(onClick = onLoad),
    )
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

    CatalogScreen(catalog, {}, {}, {})
}
