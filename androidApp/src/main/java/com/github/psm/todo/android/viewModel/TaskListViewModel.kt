package com.github.psm.todo.android.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.github.psm.shared.db.Task
import com.github.psm.todo.db.DatabaseDriverFactory
import com.github.psm.todo.db.TodoDataBase
import kotlinx.coroutines.flow.Flow
import java.util.*

class TaskListViewModel(application: Application): AndroidViewModel(application) {
    private val driverFactory = DatabaseDriverFactory(application.applicationContext)
    private val database: TodoDataBase = TodoDataBase(driverFactory)
    val tasks: Flow<List<Task>> = database.getAllTaskFlow()

    fun addNewTask(taskName: String) {
        val task = Task(
            id = UUID.randomUUID().toString(),
            title = taskName,
            isCompleted = false,
            timeStamp = System.currentTimeMillis()
        )
        database.addNewTask(task)
    }

    fun toggleCompleted(id: String) {
        database.toggleCompleted(id)
    }
}