//
//  DrawerMenuItem.swift
//  iosApp
//
//  Created by Anantasak on 3/11/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

struct DrawerMenuItem<Icon: View>: View {
    
    var icon: Icon
    var title: String
    
    init(
        title: String,
        icon: @escaping () -> Icon
    ) {
        self.icon = icon()
        self.title = title
    }
    
    var body: some View {
        HStack(spacing: 16) {
            icon
            Text(title)
            Spacer()
        }
        .padding()
    }
}

struct DrawerMenuItem_Previews: PreviewProvider {
    static var previews: some View {
        DrawerMenuItem(
            title: "Bookmark"
        ) {
            Image(systemName: "bookmark")
        }
        .frame(maxWidth: .infinity)
        .previewLayout(.sizeThatFits)
    }
}
