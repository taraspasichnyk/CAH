import SwiftUI
import shared

struct ContentView: View {

    @EnvironmentObject private var loadingState: LoadingState
    @EnvironmentObject private var alertState: AlertState
    @State private var navState: [NavPath] = []

    private let injector: Injector
    private let menuVm: MenuViewModel
    private var shareController: PasteboardControlling

    init(
        injector: Injector = Injector.shared,
        shareController: PasteboardControlling = PasteboardController.shared
    ) {
        self.injector = injector
        self.menuVm = injector.menuViewModel
        self.shareController = shareController
    }

    // MARK: - Body

    var body: some View {
        NavigationStack(path: $navState, root: {
            MainMenuView(vm: menuVm)
                .navigationDestination(for: NavPath.self) {
                    mapNavigation(path: $0)
                }
        })
        .alert(isPresented: $alertState.isPresentingAlert) {
            alertState.alert
        }
        .onAppear {
            subscribeToEffects()
        }
    }
}

// MARK: - Private

extension ContentView {
    private func subscribeToEffects() {
        AnyFlow<MenuContractEffect>(source: menuVm.effect).collect { effect in
            guard let effect else { return }
            process(effect: effect)
        } onCompletion: { _ in
        }
    }

    @ViewBuilder
    private func mapNavigation(path: NavPath) -> some View {
        switch path {
        case .enterName(let lobbyVm):
            EnterScreenView(stage: .playerName(lobbyVm))
        case .enterCode(let lobbyVm):
            EnterScreenView(stage: .roomCode(lobbyVm))
        case .lobby(let lobbyVm):
            LobbyView(vm: lobbyVm)
        case .yourCards(let gameVm):
            CardsView(gameModel: GameModel(vm: gameVm))
        case .preround(let gameVm):
            CurrentRoundView(gameModel: GameModel(vm: gameVm))
        case .round(let gameVm):
            CurrentRoundView(gameModel: GameModel(vm: gameVm))
        }
    }

    private func process(effect: MenuContractEffect) {
        MenuEffectProcessor(
            injector: injector,
            navState: $navState,
            alertState: alertState
        )
        .process(effect) { lobbyVm in
            AnyFlow<LobbyContractEffect>(source: lobbyVm.effect).collect { lobbyEffect in
                guard let lobbyEffect else { return }
                process(effect: lobbyEffect)
            } onCompletion: { _ in
            }
        }
    }

    private func process(effect: LobbyContractEffect) {
        LobbyEffectProcessor(
            injector: injector,
            navState: $navState,
            alertState: alertState,
            shareController: shareController
        ).process(effect) { gameVm in
            AnyFlow<GameContractEffect>(source: gameVm.effect).collect { gameEffect in
                guard let gameEffect else { return }
                process(effect: gameEffect)
            } onCompletion: { _ in
            }
        }
    }

    private func process(effect: GameContractEffect) {
        GameEffectProcessor(
            injector: injector,
            navState: $navState,
            alertState: alertState,
            shareController: shareController
        ).process(effect)
    }
}

// MARK: - Previews

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView()
	}
}
