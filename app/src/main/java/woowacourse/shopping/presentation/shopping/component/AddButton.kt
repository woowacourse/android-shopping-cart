package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onClick() },
    ) {
        Image(
            painter = painterResource(id = R.drawable.line),
            contentDescription = "Add",
            modifier =
                Modifier
                    .size(20.dp)
                    .align(Alignment.Center),
        )
        Image(
            painter = painterResource(id = R.drawable.line),
            contentDescription = "Add",
            modifier =
                Modifier
                    .size(20.dp)
                    .rotate(90f)
                    .align(Alignment.Center),
        )
    }
}

@Preview
@Composable
fun AddButtonPreview() {
    AddButton(
        onClick = {},
    )
}
