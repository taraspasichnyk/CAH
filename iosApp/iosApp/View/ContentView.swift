import SwiftUI
import shared

struct ContentView: View {

    @EnvironmentObject private var alert: AlertState
    @State private var navState: [NavigationState] = []

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
                .navigationDestination(for: NavigationState.self) {
                    mapNavigation(path: $0)
                }
        })
        .alert(isPresented: $alert.isPresentingNoFeature) {
            alert.noFeature
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
    private func mapNavigation(path: NavigationState) -> some View {
        switch path {
        case .enterName(let lobbyVm):
            EnterScreenView(stage: .playerName(lobbyVm))
        case .enterCode(let lobbyVm):
            EnterScreenView(stage: .roomCode(lobbyVm))
        case .lobby(let lobbyVm):
            LobbyView(vm: lobbyVm)
        default:
            EmptyView()
        }
    }

    private func process(effect: MenuContractEffect) {
        switch effect {
        case is MenuContractEffect.NavigationNewGameScreen:
            let lobbyVm = injector.lobbyOwnerViewModel
            subscribeToEffects(lobbyVm)
            navState.navigate(to: .enterName(lobbyVm))
        case is MenuContractEffect.NavigationJoinGameScreen:
            let lobbyVm = injector.lobbyViewModel
            subscribeToEffects(lobbyVm)
            navState.append(.enterCode(lobbyVm))
        default:
            alert.isPresentingNoFeature = true
        }
    }

    private func process(effect: LobbyContractEffect) {
        switch effect {
        case is LobbyContractEffect.NavigationUsersListScreen:
            if let lobbyVm = navState.compactMap(\.lobbyViewModel).last {
                navState.append(.lobby(lobbyVm))
            }
        case is LobbyContractEffect.NavigationMenuScreen:
            navState = []
        case is LobbyContractEffect.NavigationEnterCodeScreen:
            if let lobbyVm = navState.compactMap(\.lobbyViewModel).last {
                navState.navigate(to: .enterCode(lobbyVm))
            }
        case is LobbyContractEffect.NavigationEnterNameScreen:
            let lobbyVm = navState.compactMap(\.lobbyViewModel).last ?? injector.lobbyOwnerViewModel
            navState.navigate(to: .enterName(lobbyVm))
        case is LobbyContractEffect.NavigationYourCardsScreen:
            // TODO: navState.navigate(to: .yourCards)
            alert.isPresentingNoFeature = true
        case let copyCodeEffect as LobbyContractEffect.CopyCode:
            shareController.copyToPasteboard(copyCodeEffect.code)
        default:
            alert.isPresentingNoFeature = true
        }
    }

    private func subscribeToEffects(_ lobbyVm: LobbyViewModel) {
        AnyFlow<LobbyContractEffect>(source: lobbyVm.effect).collect { effect in
            guard let effect else { return }
            process(effect: effect)
        } onCompletion: { _ in
        }
    }
}

// MARK: - Previews

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView()
	}
}
