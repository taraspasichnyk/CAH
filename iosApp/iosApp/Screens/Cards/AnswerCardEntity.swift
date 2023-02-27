//
//  AnswerCardEntity.swift
//  iosApp
//
//  Created by Taras Pasichnyk on 27.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct AnswerCardEntity: Identifiable, Hashable {
    let id: String
    let text: String

    static let mock: [AnswerCardEntity] = [
        .init(id: "123", text: "Степан Гіга"),
        .init(id: "123", text: "Знімати персики з дерева біля ЖЕКу"),
        .init(id: "123", text: "Місити палкою кропиву"),
        .init(id: "123", text: "Неймовірний покемон Сквіртл"),
        .init(id: "123", text: "Картонний пакет Кагору"),
        .init(id: "123", text: "Футбольний клуб \"Карпати\""),
        .init(id: "123", text: "Майнити біткойни на Atari"),
        .init(id: "123", text: "Стрілецька Дивізія \"СС Галичина\""),
        .init(id: "123", text: "Божеволіти він нестримного програмування"),
        .init(id: "123", text: "Тім лід гомосексуаліст")
    ]
}
