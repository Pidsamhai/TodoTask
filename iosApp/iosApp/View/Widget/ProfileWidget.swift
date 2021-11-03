//
//  ProfileWidget.swift
//  iosApp
//
//  Created by Anantasak on 25/10/2564 BE.
//  Copyright © 2564 BE PSM. All rights reserved.
//

import SwiftUI

struct ProfileWidget<Image: View>: View {
    var image: Image
    var bgColor: Color
    var progressColor: Color
    var progress: CGFloat
    
    init(
        image: Image,
        bgColor: Color,
        progressColor: Color,
        progress: CGFloat
    ) {
        self.image = image
        self.bgColor = bgColor
        self.progressColor = progressColor
        self.progress = progress
    }
    
    var body: some View {
        GeometryReader { gp in
            
            let width = gp.size.width
            let height = gp.size.height
            
            ZStack {
                MyShape()
                    .frame(
                        width: width,
                        height: height
                    )
                    .foregroundColor(self.bgColor)
                
                MyShape(
                    progress: progress
                )
                    .frame(
                        width: width,
                        height: height
                    )
                    .foregroundColor(self.progressColor)
                    .shadow(
                        color: .blue, radius: 3
                    )
                image
                    .frame(
                        width: width * 0.9,
                        height: height * 0.9
                    )
                    .cornerRadius(100)
            }
        }
    }
}

struct MyShape : Shape {
    
    var progress: CGFloat
    let maxProgress: CGFloat = 360
    let startAngle: CGFloat = 270
    
    init(progress: CGFloat = 1) {
        self.progress = progress
    }
    
    func path(in rect: CGRect) -> Path {
        var p = Path()
        
        p.addArc(
            center: CGPoint(x: rect.width / 2, y: rect.height / 2),
            radius: rect.width / 2,
            startAngle: .degrees(startAngle),
            endAngle: .degrees(startAngle) + .degrees(360 * progress),
            clockwise: false
        )
        
        return p.strokedPath(
            .init(
                lineWidth: 3,
                dashPhase: 10
            )
        )
    }
}

struct ProfileWidget_Previews: PreviewProvider {
    static var previews: some View {
        ProfileWidget(
            image: Image("profile_pic")
                .resizable(),
            bgColor: .gray.opacity(0.5),
            progressColor: .green,
            progress: 0.5
        ).frame(
            width: 100,
            height: 100
        )
    }
}
