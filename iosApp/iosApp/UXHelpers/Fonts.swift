//
//  Fonts.swift
//  iosApp
//
//  Created by Artem Yelizarov on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

extension Font {
    static var button: Font { .custom("Unbounded-Regular", size: 18) }
    static var bodyPrimary: Font { .custom("Unbounded-Light", size: 16) }
    static var bodySecondary: Font { .custom("Unbounded-Medium", size: 14) }
    static var lightBodySecondary: Font { .custom("Unbounded-Light", size: 14) }
    static var bodyTertiary: Font { .custom("Unbounded-Medium", size: 12) }
    static var inputPrimary: Font { .custom("Unbounded-Medium", size: 16) }
    static var titleRegularPrimary: Font { .custom("Unbounded-Regular", size: 11) }
    static var titleRegularSecondary: Font { .custom("Unbounded-Regular", size: 24) }
    static var titleBoldSecondary: Font { .custom("Unbounded-Bold", size: 24) }

    static var titleSemiBold: Font { .custom("Unbounded-SemiBold", size: 16) }
    static var cardSmall: Font { .custom("Unbounded-Medium", size: 10) }
    static var largeTitleSemiBold: Font { .custom("Unbounded-SemiBold", size: 32) }
}
