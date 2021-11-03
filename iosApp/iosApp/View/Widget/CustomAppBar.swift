//
//  CustomAppBar.swift
//  iosApp
//
//  Created by Anantasak on 25/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

struct CustomAppBar<Nav: View, Action: View>: View {
    
    var navigation: Nav
    var actions: Action
    
    init(
        @ViewBuilder navigation: @escaping () -> Nav,
        @ViewBuilder actions: @escaping () -> Action
    ) {
        self.navigation = navigation()
        self.actions = actions()
    }
    
    var body: some View {
        HStack {
            navigation
            Spacer()
            actions
        }
        .frame(
            maxWidth: .infinity
        )
    }
}

struct CustomAppBar_Previews: PreviewProvider {
    static var previews: some View {
        CustomAppBar(
            navigation: {
                Image(systemName: "equal")
                    .padding()
            },
            actions: {
                Image(systemName: "equal")
                    .padding()
            }
        )
    }
}
