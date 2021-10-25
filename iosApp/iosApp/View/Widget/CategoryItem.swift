//
//  CategoryItem.swift
//  iosApp
//
//  Created by Anantasak on 22/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

struct CategoryItem: View {
    var body: some View {
        VStack(
            alignment: .leading,
            spacing: 20
        ) {
            Text("40 tasks")
            
            Text("Business")
                .truncationMode(.tail)
                .font(.largeTitle)
                .lineLimit(1)
            
            VStack{ }
                .frame(maxWidth: .infinity)
                .background(
                    GeometryReader { geometry in
                        ZStack {
                            Rectangle()
                                .foregroundColor(.red)
                                .frame(
                                    width: geometry.size.width,
                                    height: 10
                                )
                                .cornerRadius(50)
                        }
                    }
                )
                .padding(.bottom)
        }
        .padding(16)
        .background(Color.white)
        .cornerRadius(10)
        .shadow(radius: 8)
    }
}

struct CategoryItem_Previews: PreviewProvider {
    static var previews: some View {
        CategoryItem()
            .previewLayout(.sizeThatFits)
            .padding()
    }
}
