//
//  UndoButton.swift
//  iosApp
//
//  Created by Anantasak on 26/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

struct UndoButton: View {
    
    var action: () -> ()
    
    init(action: @escaping () -> ()) {
        self.action = action
    }
    
    var body: some View {
        Button {
            action()
        } label: {
            Text("Undo".uppercased())
        }
        .overlay(
            RoundedRectangle(
                cornerRadius: 100)
                .stroke(
                    Color.gray,
                    lineWidth: 2
                )
        )

    }
}

struct UndoButton_Previews: PreviewProvider {
    static var previews: some View {
        UndoButton(
            action: {
                
            }
        )
    }
}
