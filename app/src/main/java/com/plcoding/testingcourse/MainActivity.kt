package com.plcoding.testingcourse

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.plcoding.testingcourse.part12.presentation.ProfileActivity
import com.plcoding.testingcourse.ui.theme.TestingCourseTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestingCourseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    ProfileScreen(state = previewProfileState())

                    Button(onClick = {
                        Intent(applicationContext, ProfileActivity::class.java).also {
                            it.putExtra("TEST_EXTRA", "top secret")
                            it.action = "MY_ACTION"

                            startActivity(it)
                        }
                    }) {
                        Text(text = "Send intent")
                    }
                }
            }
        }
    }
}