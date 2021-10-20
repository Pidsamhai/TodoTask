package com.github.psm.todo.db

import com.github.psm.shared.db.AppDatabase
import com.github.psm.shared.db.Task
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TodoDataBase(databaseDriverFactory: DatabaseDriverFactory) {

    private val database: AppDatabase = AppDatabase(databaseDriverFactory.createDriver())

    fun getAllTask(): List<Task> {
        return database.appdatabaseQueries.getAllTasks().executeAsList()
    }

    fun getAllTaskFlow(): Flow<List<Task>> {
        return database.appdatabaseQueries.getAllTasks()
            .asFlow()
            .mapToList()
    }

    fun getTask(id: String): Task? {
        return database.appdatabaseQueries.getTask(id).executeAsOneOrNull()
    }

    fun deleteAllTasks() {
        database.appdatabaseQueries.deleteAllTasks()
    }

    fun deleteTask(id: String) {
        database.appdatabaseQueries.deleteTask(id)
    }

    fun addNewTask(task: Task) {
        database.appdatabaseQueries.insertTask(
                id = task.id,
                title = task.title,
                isCompleted = task.isCompleted,
                timeStamp = task.timeStamp
            )
    }

    fun toggleCompleted(id: String) {
        database.transaction {
            val task = database.appdatabaseQueries.getTask(id).executeAsOneOrNull() ?: rollback()
            database.appdatabaseQueries.updateCompleted(
                isCompleted = !task.isCompleted,
                id = task.id
            )
        }
    }
}