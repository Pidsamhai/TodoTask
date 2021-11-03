//
//  ViewExt.swift
//  iosApp
//
//  Created by Anantasak on 22/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import Foundation
import SwiftUI

struct FloatingActionButtonStyle: ButtonStyle {
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding()
            .background(
                configuration.isPressed ? Color.blue.opacity(0.8) : Color.blue
            )
            .clipShape(Circle())
            .shadow(
                color: .accentColor.opacity(0.5),
                radius: 2,
                x: 3,
                y: 3
            )
            .padding()
    }
}
