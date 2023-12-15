package com.jaehi.molgam

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaehi.molcardogam.Molcar
import com.jaehi.molcardogam.MolcarData
import com.jaehi.molgam.ui.theme.MolcarDogamTheme
import com.jaehi.molgam.ui.theme.Season1
import com.jaehi.molgam.ui.theme.Season2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MolcarDogamTheme() {
                MolcarApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MolcarApp() {
    Scaffold() {
        RecyclerViewContent()
    }
}

@Composable
@Preview(showBackground = true)
fun RecyclerViewContent() {
    val molcar = remember { MolcarData.molcarList }
    LazyColumn(
        contentPadding = PaddingValues(20.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                Color.White
            )
            .fillMaxSize())
    {
        items(
            items = molcar,
            itemContent = { RecyclerItem(it) },
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RecyclerItem(molcar: Molcar) {
    var expanded by remember { mutableStateOf(false) }
    var default by remember { mutableStateOf(true) }

    Card(
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(2.dp, if (molcar.season == 1) Season1 else Season2),
        modifier = if (expanded) Modifier
            .padding(4.dp)
            .fillMaxWidth() else Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        AnimatedVisibility(visible = expanded,   exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically) + fadeOut(targetAlpha = 0f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .animateContentSize()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        expanded = !expanded
                        default = !default
                    }) {
                Image(molcar = molcar, expanded)
                Text(
                    text = molcar.name,
                    Modifier.padding(4.dp),
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = molcar.introduce,
                    Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_expand_less_24),
                    contentDescription = "Molcar",
                    colorFilter = ColorFilter.tint(if (molcar.season == 1) Season1 else Season2)
                )
            }
        }

        AnimatedVisibility(visible = default, enter = fadeIn(initialAlpha = 0.4f),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically) + fadeOut(targetAlpha = 0f)) {
            Row(modifier = Modifier
                .animateContentSize()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    expanded = !expanded
                    default = !default
                }
                .fillMaxWidth()
                .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Image(molcar = molcar, expanded)
                Text(
                    text = molcar.name,
                    Modifier
                        .padding(4.dp)
                        .weight(1F),
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_expand_more_24),
                    contentDescription = "Molcar",
                    colorFilter = ColorFilter.tint(if (molcar.season == 1) Season1 else Season2),
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .offset(x = (-4).dp)
                )
            }
        }
    }
}

@Composable
fun Image(molcar: Molcar, expanded: Boolean) {
    Image(
        painter = painterResource(id = molcar.image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(if (expanded) 240.dp else 60.dp)
            .clip(RoundedCornerShape(CornerSize(360.dp)))
    )

}