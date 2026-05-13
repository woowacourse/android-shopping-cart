package woowacourse.shopping.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R

@Composable
fun AddCircleButton(
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClickAdd,
        modifier = modifier
            .size(48.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xff555555),
        ),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "상품 추가",
            modifier = Modifier.fillMaxSize(),
            tint = Color(0xff555555),
        )
    }
}

@Composable
@Preview
private fun AddCircleButtonPreview() {
    AddCircleButton(onClickAdd = {})
}
