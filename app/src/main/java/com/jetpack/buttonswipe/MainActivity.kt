package com.jetpack.buttonswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.jetpack.buttonswipe.ui.theme.ButtonSwipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonSwipeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "MotionLayout Animation",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        MotionLayoutAnimation()
                    }
                }
            }
        }
    }
}

@Composable
fun MotionLayoutAnimation() {
    var animateToEnd by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animateToEnd) 1f else 0f,
        animationSpec = tween(2000)
    )
    val baseConstraintSet = """
        {
            Variables: {
                angle: { from: 0, step: 10 },
                rotation: { from: 'startRotation', step: 10 },
                distance: 100,
                mylist: { tag: 'box' }
            },
            Generate: {
                mylist: {
                    width: 200,
                    height: 40,
                    circular: ['parent', 'angle', 'distance'],
                    pivotX: 0.1,
                    pivotY: 0.1,
                    translationX: 225,
                    rotationZ: 'rotation'
                }
            }
        }
    """.trimIndent()

    val cs1 = ConstraintSet(baseConstraintSet, overrideVariables = "{ startRotation: 0 }")
    val cs2 = ConstraintSet(baseConstraintSet, overrideVariables = "{ startRotation: 90 }")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                animateToEnd = !animateToEnd
            }
        ) {
            Text(text = "Run")
        }

        MotionLayout(
            cs1,
            cs2,
            progress = progress,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val colors = arrayListOf(Color.Red, Color.Green, Color.Blue, Color.Cyan, Color.Yellow)

            for (i in 1..36) {
                Box(
                    modifier = Modifier
                        .layoutId("hi$i", "box")
                        .background(colors[i % colors.size])
                )
            }
        }
    }
}






















