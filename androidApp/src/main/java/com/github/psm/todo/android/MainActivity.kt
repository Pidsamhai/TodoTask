package com.github.psm.todo.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.psm.todo.Greeting
import com.github.psm.todo.android.navigation.NavRoute
import com.github.psm.todo.android.ui.page.AddTaskPage
import com.github.psm.todo.android.ui.page.TodoListPage
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import com.github.psm.todo.android.viewModel.TaskListViewModel
import com.github.psm.todo.db.DatabaseDriverFactory
import com.github.psm.todo.db.TodoDataBase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApplicationTheme {
                BodyContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BodyContent() {
    val navController = rememberNavController()
    val viewModel: TaskListViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavRoute.TaskListPage.route
    ) {
        composable(route = NavRoute.TaskListPage.route) {
            TodoListPage(
                viewModel = viewModel,
                navigateToAddTask = {
                    navController.navigate(NavRoute.AddTaskPage.route)
                }
            )
        }

        composable(route = NavRoute.AddTaskPage.route) {
            AddTaskPage(
                viewModel = viewModel,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}