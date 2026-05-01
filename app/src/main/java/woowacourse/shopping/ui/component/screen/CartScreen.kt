package woowacourse.shopping.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.component.frame.CommonFrame
import woowacourse.shopping.ui.component.item.CartItem
import java.util.UUID
import kotlin.math.min

@Composable
fun CartScreen(
    cart: Cart,
    currentPage: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onClose: () -> Unit,
    onDelete: (UUID) -> Unit,
    previousEnable: Boolean,
    nextEnable: Boolean,
    modifier: Modifier = Modifier,
) {
    CommonFrame(
        headerContent = { CartHeader(onClose) },
        bodyContent = {
            CartBody(
                cart = cart,
                onDelete = onDelete,
                currentPage = currentPage,
                onPrevious = onPrevious,
                previousEnable = previousEnable,
                nextEnable = nextEnable,
                onNext = onNext,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun CartHeader(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = "뒤로가기 버튼",
            modifier =
                Modifier
                    .size(40.dp)
                    .clickable(onClick = onClose),
            tint = Color.White,
        )
        Spacer(Modifier.padding(12.dp))
        Text(
            text = "Cart",
            fontWeight = FontWeight(500),
            fontSize = 20.sp,
            color = Color.White,
        )
    }
}

@Composable
private fun CartBody(
    cart: Cart,
    currentPage: Int,
    onDelete: (UUID) -> Unit,
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {},
    previousEnable: Boolean,
    nextEnable: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val products = cart.products
        products.getPartedItem(currentPage).forEach {
            CartItem(
                product = it,
                onDelete = onDelete,
                modifier = Modifier.padding(top = 24.dp),
            )
        }
        if (cart.isPageable()) {
            PagingBtn(
                currentPage = currentPage,
                onPrevious = onPrevious,
                previousEnable = previousEnable,
                nextEnable = nextEnable,
                onNext = onNext,
            )
        }
    }
}

@Composable
fun PagingBtn(
    currentPage: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    previousEnable: Boolean,
    nextEnable: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .padding(vertical = 30.dp)
                .width(129.dp)
                .height(42.dp)
                .clip(
                    RoundedCornerShape(4.dp),
                ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "<",
            fontSize = 22.sp,
            fontWeight = FontWeight(500),
            color = Color.White,
            modifier =
                Modifier
                    .background(color = btnAvailable(previousEnable))
                    .fillMaxHeight()
                    .width(42.dp)
                    .clickable(
                        onClick = onPrevious,
                        enabled = previousEnable,
                    )
                    .wrapContentSize(Alignment.Center),
        )
        Text(
            text = (currentPage + 1).toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF555555),
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(42.dp)
                    .wrapContentSize(Alignment.Center),
        )
        Text(
            text = ">",
            fontSize = 22.sp,
            fontWeight = FontWeight(500),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(42.dp)
                    .clickable(
                        onClick = onNext,
                        enabled = nextEnable,
                    )
                    .background(color = btnAvailable(nextEnable))
                    .wrapContentHeight(Alignment.CenterVertically),
        )
    }
}

private fun btnAvailable(btnFlag: Boolean): Color =
    if (btnFlag) Color(0xFF04C09E) else Color(0xFFAAAAAA)

private fun Cart.isPageable(): Boolean = products.size() > 5

private fun Products.getPartedItem(
    page: Int,
    pageSize: Int = 5,
): List<Product> {
    val fromIndex = page * pageSize
    val toIndex = min(fromIndex + pageSize, this@getPartedItem.products.size)
    return this@getPartedItem.products.subList(fromIndex, toIndex)
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        currentPage = 0,
        onClose = {},
        onDelete = {},
        onPrevious = {},
        onNext = {},
        previousEnable = false,
        nextEnable = false,
        cart =
            Cart(
                products =
                    Products(
                        listOf(
                            Product(
                                imageUri = "uri",
                                name = "무엘사",
                                price = 10000000,
                            ),
                            Product(
                                imageUri = "uri",
                                name = "무엘사",
                                price = 10000000,
                            ),
                            Product(
                                imageUri = "uri",
                                name = "무엘사",
                                price = 10000000,
                            ),
                            Product(
                                imageUri = "uri",
                                name = "무엘사",
                                price = 10000000,
                            ),
                            Product(
                                imageUri = "uri",
                                name = "무엘사",
                                price = 10000000,
                            ),
                            Product(
                                imageUri = "uri",
                                name = "무엘사",
                                price = 10000000,
                            ),
                        ),
                    ),
            ),
    )
}
