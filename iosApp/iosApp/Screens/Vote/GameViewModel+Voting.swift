//
//  GameViewModel+Voting.swift
//  iosApp
//
//  Created by Artem Yelizarov on 09.03.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

protocol VoteGameViewModelProtocol {
    func saveScores(answerCardWithVotes: [RoundPlayerAnswer])
}

extension GameViewModel: VoteGameViewModelProtocol {}

// MARK: - Mock

final class MockVoteGameViewModel: VoteGameViewModelProtocol {
    func saveScores(answerCardWithVotes: [RoundPlayerAnswer]) {
        // TODO
    }
}
