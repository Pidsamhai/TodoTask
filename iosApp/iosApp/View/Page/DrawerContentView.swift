//
//  DrawerContentView.swift
//  iosApp
//
//  Created by Anantasak on 25/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

struct DrawerContentView: View {
    
    let onClose: () -> ()
    
    init(onClose: @escaping () -> () = { }) {
        self.onClose = onClose
    }
    
    var body: some View {
        ZStack(
            alignment: .topTrailing
        ) {
            Image(systemName: "chevron.left")
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
                .foregroundColor(.shadowColor)
                .onTapGesture {
                    self.onClose()
                }
            
            VStack(
                spacing: 20
            ) {
                ProfileWidget(
                    image: Image("profile_pic")
                        .resizable(),
                    bgColor: .shadowColor.opacity(0.5),
                    progressColor: .progressColor,
                    progress: 0.8
                ).frame(
                    width: 80,
                    height: 80
                )
                
                Text("Joy Mitchell")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                
                VStack {
                    DrawerMenuItem(
                        title: "Template"
                    ) {
                        Image(systemName: "bookmark")
                    }
                    
                    DrawerMenuItem(
                        title: "Categories"
                    ) {
                        Image(systemName: "square.grid.2x2")
                    }
                    
                    DrawerMenuItem(
                        title: "Analytics"
                    ) {
                        Image(systemName: "chart.pie")
                    }
                    
                    DrawerMenuItem(
                        title: "Setting"
                    ) {
                        Image(systemName: "gearshape")
                    }
                }
                
            }
            .foregroundColor(.white)
            .padding(.top, 100)
            .frame(
                maxWidth: .infinity,
                maxHeight: .infinity,
                alignment: .top
            )
        }
    }
}

struct DrawerContentView_Previews: PreviewProvider {
    static var previews: some View {
        DrawerContentView()
            .background(Color.bgDark)
    }
}
