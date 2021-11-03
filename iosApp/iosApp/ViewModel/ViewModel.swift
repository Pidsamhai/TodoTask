//
//  ViewModel.swift
//  iosApp
//
//  Created by Anantasak on 21/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import Foundation
import shared

class ViewModel: ObservableObject {
    private var database: TodoDataBase = TodoDataBase(databaseDriverFactory: DatabaseDriverFactory())
    
    @Published var tasks: [Task] = []
    
    init() {
        loadData()
    }
    
    func loadData() {
        database.getAllTaskCommon()
            .watch { array in
                if let _tasks = array as? [Task] {
                    self.tasks = _tasks
                }
            }
    }
    
    func toggleCompleted(id: String) {
        database.toggleCompleted(id: id)
    }
    
    func delete(indexSet: IndexSet) {
        for index in indexSet {
            database.deleteTask(id: tasks[index].id)
        }
    }
    
    func delete(id: String) {
        database.deleteTask(id: id)
    }
    
    func addTask(title: String) {
        let task: Task = Task(
            id: UUID().uuidString,
            title: title,
            isCompleted: false,
            timeStamp: 0
        )
        database.addNewTask(task: task)
    }
}
