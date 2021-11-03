//
//  AddTaskPage.swift
//  iosApp
//
//  Created by Anantasak on 22/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI
import shared

struct AddTaskPage: View {
    
    @State var title: String = ""
    @EnvironmentObject var viewModel: ViewModel
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        ZStack(alignment: .topTrailing) {
            
            Image(systemName: "xmark")
                .padding()
                .overlay(
                    RoundedRectangle(
                        cornerRadius: 100)
                        .stroke(
                            Color.shadowColor,
                            lineWidth: 2
                        )
                )
                .padding(10)
                .onTapGesture {
                    presentationMode.wrappedValue.dismiss()
                }

            
            VStack(
                alignment: .center
            ) {
                TextField("Enter task name", text: $title)
            }
            .frame(
                maxWidth: .infinity,
                maxHeight: .infinity
            )
            
            VStack {
                Spacer()
                HStack {
                    Text("New task")
                    Image(systemName: "chevron.up")
                }
                .foregroundColor(.white)
                .padding()
                .background(Color.blue)
                .cornerRadius(100)
                .shadow(
                    color: .accentColor.opacity(0.5),
                    radius: 2,
                    x: 3,
                    y: 3
                )
                .onTapGesture {
                    viewModel.addTask(title: title)
                    presentationMode.wrappedValue.dismiss()
                }
            }
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity
        )
        .navigationBarHidden(true)
        .padding()
    }
}

struct AddTaskPage_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            AddTaskPage()
        }
        .environmentObject(ViewModel())
    }
}
