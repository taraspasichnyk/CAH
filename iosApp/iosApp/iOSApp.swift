import SwiftUI
import shared

@main
struct iOSApp: App {

    @State var state: GameContractGameState = GameContractGameState.InMenu()
    var vm: GameViewModel!

    init() {
        LoggerKt.doInit()
        KoinIosKt.doInitKoin()

        vm = Injector.shared.gameViewModel as! GameViewModel
    }

    // MARK: - Body

	var body: some Scene {
		WindowGroup {
			ContentView(
                state: $state,
                vm: vm
            )
            .onAppear {
                subscribeToState()
                for family in UIFont.familyNames.sorted() {
                    let names = UIFont.fontNames(forFamilyName: family)
                    print("Family: \(family) Font names: \(names)")
                }
            }
		}
	}
}

// MARK: - Private

extension iOSApp {
    private func subscribeToState() {
        AnyFlow<GameContractGameState>(source: vm.state).collect { state in
            guard let state else { return }
            self.state = state
        } onCompletion: { _ in
        }
    }
}
