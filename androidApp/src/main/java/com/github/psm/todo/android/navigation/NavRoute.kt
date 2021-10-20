package com.github.psm.todo.android.navigation

sealed class NavRoute(val route: String) {
    object TaskListPage: NavRoute(route = "todo_list_page")
    object AddTaskPage: NavRoute(route = "add_task_page")
}