//
//  CategoryItem.swift
//  iosApp
//
//  Created by Anantasak on 22/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

struct CategoryItem: View {
    var title: String
    var color: Color
    /** range 0..1 */
    var percent: CGFloat
    
    init(
        title: String,
        color: Color,
        percent: CGFloat = 1
    ) {
        self.title = title
        self.color = color
        self.percent = percent
    }
    
    var body: some View {
        VStack(
            alignment: .leading,
            spacing: 20
        ) {
            Text("40 tasks")
            
            Text(title)
                .truncationMode(.tail)
                .font(.largeTitle)
                .lineLimit(1)
            
            VStack{ }
            .frame(maxWidth: .infinity)
            .background(
                GeometryReader { geometry in
                    ZStack {
                        Rectangle()
                            .foregroundColor(color)
                            .frame(
                                width: geometry.size.width * percent,
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
        CategoryItem(
            title: "Business",
            color: Color.blue,
            percent: 0.9
        )
            .previewLayout(.sizeThatFits)
            .padding()
    }
}
