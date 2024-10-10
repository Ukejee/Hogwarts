package com.ukejee.hogwarts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.ukejee.hogwarts.ui.characters.viewmodels.CharactersViewModel
import com.ukejee.hogwarts.ui.navigation.RootNavigationGraph
import com.ukejee.hogwarts.ui.theme.HogwartsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var  viewModel: CharactersViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        enableEdgeToEdge()
        setContent {
            HogwartsTheme {
                val appBarState = viewModel.appBarState.collectAsState().value
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (appBarState.showBackButton) {
                            TopAppBar(
                                title = { Text(text = appBarState.title) },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            viewModel.onBackButtonPressed()
                                            navController.popBackStack()
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(64.dp),
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                    },
                ) { innerPadding ->
                    RootNavigationGraph(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[CharactersViewModel::class.java]
    }
}
