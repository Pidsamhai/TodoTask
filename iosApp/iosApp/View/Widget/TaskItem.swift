//
//  TaskItem.swift
//  iosApp
//
//  Created by Anantasak on 26/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI
import shared

struct TaskItem: View {
    private static let oneSecond = 1_000_000_000
    @State var offsetX: CGFloat = 0
    @State var buttonOffset: CGFloat = 100
    @State var showHint: Bool = false
    @State var isOpen: Bool = false
    @State var queue = OperationQueue()
    var onDelete: () -> ()
    var onClick: () -> ()
    
    var task: Task
    
    init(
        task: Task,
        onDelete: @escaping () -> (),
        onClick: @escaping () -> () = {}
    ) {
        self.task = task
        self.onDelete = onDelete
        self.onClick = onClick
    }
    
    private func delayAction() {
        self.queue.addOperation {
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                if isOpen {
                    onDelete()
                }
            }
        }
    }
    
    private func cancelQueue() {
        self.queue.cancelAllOperations()
    }
    
    private var gesture: some Gesture {
        DragGesture(minimumDistance: 0,coordinateSpace: .global)
           .onChanged { value in
               let width: CGFloat = value.translation.width
               /**
                Open
                */
               if width < 0 {
                   self.open()
               }
           }
    }
    
    private func open() {
        withAnimation(.spring()) {
            offsetX = -20
            buttonOffset = 0
            showHint = true
        }
        
        if !isOpen {
            delayAction()
            isOpen = true
        }
    }
    
    private func close() {
        withAnimation(.spring()) {
            offsetX = 0
            buttonOffset = 100
            showHint = false
        }
        isOpen = false
    }
    
    var body: some View {
        ZStack() {
            if !showHint {
                HStack() {
                    Image(
                        systemName: task.isCompleted ?
                        "checkmark.circle.fill" : "circle"
                    ).foregroundColor(task.isCompleted ? .shadowColor : .red)
                    Text(task.title)
                        .strikethrough(task.isCompleted)
                        .frame(
                            maxWidth: .infinity,
                            alignment: .leading
                        )
                }
            }  else {
                HStack(
                    spacing: 16
                ) {
                    Image(systemName: "trash")
                    Text("The task was deleted")
                }
                .frame(
                    maxWidth: .infinity,
                    alignment: .leading
                )
            }
            
            HStack {
                Spacer()
                Text("Undo")
                    .onTapGesture {
                        close()
                        cancelQueue()
                    }
                    .padding(.horizontal, 10)
                    .padding(.vertical, 5)
                    .frame(
                        alignment: .trailing
                    )
                    .overlay(
                        RoundedRectangle(
                            cornerRadius: 100)
                            .stroke(
                                Color.shadowColor,
                                lineWidth: 2
                            )
                    )
                    .offset(
                        x: buttonOffset
                    )
            }
            
        }
        .contentShape(Rectangle())
        .frame(maxWidth: .infinity)
        .onTapGesture {
            onClick()
        }
        .gesture(gesture)
    }
}

struct TaskItem_Previews: PreviewProvider {
    static var previews: some View {
        let tasks: [Task] = [
            Task(id: "1", title: "title", isCompleted: false, timeStamp: 0),
            Task(id: "2", title: "title", isCompleted: true, timeStamp: 0)
        ]
        List {
            ForEach(tasks) { task in
                TaskItem(
                    task: task,
                    onDelete: {
                        
                    }
                ).frame(maxWidth: .infinity)
            }
        }
    }
}
