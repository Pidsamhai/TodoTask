//
//  iosApp.swift
//  iosApp
//
//  Created by Anantasak on 22/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

@main
struct iOSApp: App {
    
    @StateObject var viewModel = ViewModel()
    @State var isOpen: Bool = false
    
    var body: some Scene {
        WindowGroup {
            RootView {
                NavigationView{
                    AppDrawer(
                        content: TaskListPage(
                            openDrawer: {
                                withAnimation {
                                    self.isOpen.toggle()
                                }
                            }
                        ),
                        drawerContent: DrawerContentView(
                            onClose: {
                                withAnimation {
                                    self.isOpen = false
                                }
                            }
                        ),
                        isOpen: $isOpen,
                        overlaps: 50
                    ).background(Color.bgDark)
                }
                .environmentObject(viewModel)
            }
        }
    }
}
