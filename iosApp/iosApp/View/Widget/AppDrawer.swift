//
//  AppDrawer.swift
//  iosApp
//
//  Created by Anantasak on 25/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

struct AppDrawer<C: View, D: View>: View {
    @Binding var isOpen: Bool
    var content: C
    var drawerContent: D
    let width = UIScreen.main.bounds.width
    @State var drawerOffset: CGFloat = -UIScreen.main.bounds.width
    @State var contentOffset: CGFloat = 0
    var overlaps: CGFloat
    
    init(
        @ViewBuilder content: @escaping () -> C,
        @ViewBuilder drawerContent: @escaping () -> D,
        isOpen: Binding<Bool>,
        overlaps: CGFloat = 0
    ) {
        self.overlaps = overlaps
        self._isOpen = isOpen
        self.content = content()
        self.drawerContent = drawerContent()
    }
    
    init(
        content: C,
        drawerContent: D,
        isOpen: Binding<Bool>,
        overlaps: CGFloat = 0
    ) {
        self.overlaps = overlaps
        self._isOpen = isOpen
        self.content = content
        self.drawerContent = drawerContent
    }
    
    var body: some View {
        GeometryReader { gp in
            ZStack(alignment: .top) {
                
                Rectangle()
                    .foregroundColor(
                        self.isOpen ? .bgDark : .white
                    )
                    .edgesIgnoringSafeArea(.top)
                
                /**
                 Content Block
                 */
                VStack {
                    content
                }
                .cornerRadius( self.isOpen ? 32 : 0 )
                .padding(.vertical, self.isOpen ? 32 : 0)
                .frame(
                    maxWidth: .infinity,
                    maxHeight: .infinity
                )
                .offset(
                    x: self.isOpen ? self.width - overlaps : 0
                )
                
                /**
                 Drawer Content Block
                 */
                VStack {
                    drawerContent
                }
                .padding(.trailing, overlaps)
                .frame(
                    maxHeight: .infinity
                )
                .frame(
                    width: abs(gp.size.width - overlaps)
                )
                .offset(
                    x: self.isOpen ? 0 : -self.width
                )
            }
            .statusBarStyle(
                self.isOpen  ? .lightContent : .darkContent
            )
            .frame(
                maxWidth:.infinity,
                maxHeight: .infinity
            )
        }
    }
}

private struct AppDrawerContentPrev: View {
    @State var isOpen: Bool = false
    var body: some View {
        AppDrawer(
            content: VStack {
                Text("Content")
            }.frame(
                maxWidth: .infinity,
                maxHeight: .infinity
            )
                .background(Color.white)
                .navigationBarItems(
                    leading: Image(systemName: "equal")
                        .foregroundColor(.black)
                        .onTapGesture {
                            withAnimation {
                                isOpen.toggle()
                            }
                        },
                    trailing: EmptyView()
                ),
            drawerContent: VStack {
                Text("Drawer Content")
            }.foregroundColor(.white),
            isOpen: $isOpen,
            overlaps: 50
        )
    }
    
}

struct AppDrawer_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            AppDrawerContentPrev()
        }
        .environmentObject(ViewModel())
    }
}
