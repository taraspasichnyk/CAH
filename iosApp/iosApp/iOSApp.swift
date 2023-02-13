import SwiftUI
import shared

@main
struct iOSApp: App {

    @State private var state: GameContractGameState = GameContractGameState.InMenu()
    @StateObject private var alert = AlertState()
    var vm: GameViewModel!

    init() {
        LoggerKt.doInit()
        KoinIosKt.doInitKoin()

        vm = Injector.shared.gameViewModel

        // TODO: If possible, move it out into shared
        // Make sure anonymous login is called before using Firebase
        Injector.shared.anonymousLogin.invoke { error in
            guard let error else { return }
            // TODO: Maybe check for connectivity errors, otherwise do nothing or enter error state
            print(error)
        }
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
            }
            .environmentObject(alert)
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
