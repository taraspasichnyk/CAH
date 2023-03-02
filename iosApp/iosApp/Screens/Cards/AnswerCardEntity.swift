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
}

extension AnswerCardEntity {
    static let placeholder: Self = .init(id: "", text: "")
}

extension AnswerCardEntity {
    static let mock: [AnswerCardEntity] = [
        .init(id: "1231", text: "Степан Гіга"),
        .init(id: "1232", text: "Знімати персики з дерева біля ЖЕКу"),
        .init(id: "1233", text: "Місити палкою кропиву"),
        .init(id: "1234", text: "Неймовірний покемон Сквіртл"),
        .init(id: "1235", text: "Картонний пакет Кагору"),
        .init(id: "1236", text: "Футбольний клуб \"Карпати\""),
        .init(id: "1237", text: "Майнити біткойни на Atari"),
        .init(id: "1238", text: "Стрілецька Дивізія \"СС Галичина\""),
        .init(id: "1239", text: "Божеволіти він нестримного програмування"),
        .init(id: "1230", text: "Тім лід гомосексуаліст")
    ]
}
