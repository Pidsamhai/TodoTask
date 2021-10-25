package com.github.psm.todo.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.psm.todo.android.navigation.NavRoute
import com.github.psm.todo.android.ui.page.AddTaskPage
import com.github.psm.todo.android.ui.page.AppDrawerPager
import com.github.psm.todo.android.ui.page.TodoListPage
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import com.github.psm.todo.android.viewModel.TaskListViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApplicationTheme {

                val drawerState = rememberDrawerState()

                CustomDrawerScaffold(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawerPager(
                            closeDrawer = {
                                drawerState.close()
                            }
                        )
                    },
                    content = {
                        BodyContent(
                            openDrawer = {
                                drawerState.toggle()
                                Log.i("TAG", "onCreate: a")
                            }
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BodyContent(
    openDrawer: () -> Unit = { }
) {
    val navController = rememberNavController()
    val viewModel: TaskListViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavRoute.TaskListPage.route
    ) {
        composable(route = NavRoute.TaskListPage.route) {
            TodoListPage(
                openDrawer = openDrawer,
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