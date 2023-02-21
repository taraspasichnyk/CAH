//
//  ArrayTests.swift
//  iosAppTests
//
//  Created by Andrii Puhach on 07.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import XCTest
@testable import iosApp

final class ArrayTests: XCTestCase {

    private var sut: [Int] = []

    // MARK: - Lifecycle

    override func setUp() {
        sut = [10, 20, 30, 40, 50]
    }

    override func tearDown() {
        sut = []
    }

    // MARK: - Tests

    func test_removeAllAfter_withValidIndexAndMultipleValues_removesCorrectAmountOfElements() {
        sut.removeAll(after: 2)
        XCTAssertEqual(sut, [10, 20, 30])
    }

    func test_removeAllAfter_zeroIndex_removesAllExceptFirstElement() {
        sut.removeAll(after: 0)
        XCTAssertEqual(sut, [10])
    }

    func test_removeAllAfter_invalidIndex_leavesArrayUnchanged() {
        let sutCopy = sut
        sut.removeAll(after: 88)
        XCTAssertEqual(sut, sutCopy)
    }

    func test_removeAllAfter_whenArrayIsEmpty_leavesArrayUnchanged() {
        sut = []
        sut.removeAll(after: 0)
        XCTAssertEqual(sut, [])
    }

    func test_removeAllAfter_withSingleValue_removesValueFromArray() {
        sut = [123]
        sut.removeAll(after: 0)
        XCTAssertEqual(sut, [])
    }

    func test_removeAllAfter_withTwoValues_removesOneLastValue() {
        sut = [23, 45]
        sut.removeAll(after: 0)
        XCTAssertEqual(sut, [23])
    }
}
