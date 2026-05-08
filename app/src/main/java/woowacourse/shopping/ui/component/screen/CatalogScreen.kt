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
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.ui.component.frame.CommonFrame
import woowacourse.shopping.ui.component.item.CartCountLabel
import woowacourse.shopping.ui.component.item.ShoppingItem
import java.util.UUID

@Composable
fun CatalogScreen(
    catalog: Products,
    totalCount: () -> Int,
    specificProductCount: (UUID) -> Int,
    onItemClick: (Product) -> Unit,
    onCartClick: () -> Unit,
    onLoadClick: () -> Unit,
    onAdd: (UUID, Int) -> Unit,
    onMinus: (UUID, Int) -> Unit,
    onDelete: (UUID) -> Unit,
    onAddInCart: (PurchaseProduct) -> Unit,
    isContainedInCart: (UUID) -> Boolean,
    modifier: Modifier = Modifier,
) {
    CommonFrame(
        headerContent = { CatalogHeader(totalCount, onCartClick) },
        bodyContent = {
            CatalogBody(
                catalog = catalog,
                onItemClick = { onItemClick(it) },
                onLoadClick = onLoadClick,
                onAdd = { uuid, updateAmount ->
                    onAdd(uuid, updateAmount)
                },
                onMinus = { uuid, updateAmount ->
                    onMinus(uuid, updateAmount)
                },
                onDelete = { onDelete(it) },
                onAddInCart = { onAddInCart(it) },
                isContainedInCart = isContainedInCart,
                specificProductCount = { 
                    specificProductCount(it)
                }
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun CatalogHeader(
    totalCount: () -> Int,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = "Shopping",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )
        Row{
            Icon(
                painter = painterResource(R.drawable.ic_cart),
                contentDescription = "장바구니 아이콘",
                tint = Color.White,
                modifier =
                    Modifier
                        .size(24.dp)
                        .clickable(onClick = onCartClick),
            )
            CartCountLabel(totalCount())
        }
    }
}

@Composable
private fun CatalogBody(
    catalog: Products,
    specificProductCount: (UUID) -> Int,
    onItemClick: (Product) -> Unit,
    onAddInCart: (PurchaseProduct) -> Unit,
    onAdd: (UUID, Int) -> Unit,
    onMinus: (UUID, Int) -> Unit,
    onDelete: (UUID) -> Unit,
    onLoadClick: () -> Unit,
    isContainedInCart: (UUID) ->  Boolean,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
    ) {
        items(catalog.size()) { item ->
            ShoppingItem(
                product = catalog.getSingleItem(item),
                onClick = {
                    onItemClick(catalog.getSingleItem(item))
                },
                count = {
                    specificProductCount(catalog.getSingleItem(item).uuid)
                },
                isContainedInCart = {
                    isContainedInCart(catalog.getSingleItem(item).uuid)
                },
                onAdd = {
                    onAdd(catalog.getSingleItem(item).uuid, 1)
                },
                onMinus = {
                    onMinus(catalog.getSingleItem(item).uuid, -1)
                },
                onDelete = {
                    onDelete(catalog.getSingleItem(item).uuid)
                },
                onAddInCart = { onAddInCart(it) }
            )
        }

        item(
            span = { GridItemSpan(maxLineSpan) },
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
        modifier =
            modifier
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
    val catalog =
        Products(
            listOf(
                Product(
                    imageUri = "hello",
                    name = "너무너무너무긴아이템이름",
                    price = 100000,
                ),
                Product(
                    imageUri = "디디",
                    name = "당근주스",
                    price = 1000,
                ),
                Product(
                    imageUri = "hello",
                    name = "우유",
                    price = 100,
                ),
                Product(
                    imageUri = "hello",
                    name = "투핸더",
                    price = 100000000,
                ),
            ),
        )

    CatalogScreen(
        catalog,
        totalCount = { 10 },
        specificProductCount = { it -> 0 },
        onItemClick = {  },
        onCartClick = {  },
        onLoadClick = {  },
        onAdd = { uuid, type -> },
        onMinus = { uuid, type -> },
        onDelete = {  },
        onAddInCart = {  },
        isContainedInCart = { it -> true },
    )
}
