import SwiftUI
import shared

struct ContentView: View {
    @Binding var state: GameContractGameState
    let vm: GameViewModel
    let menuVm: MenuViewModel = Injector.shared.menuViewModel
    
    @EnvironmentObject
    private var alert: AlertState
    @State private var navState: [NavigationState] = []

    // MARK: - Body

    var body: some View {
        NavigationStack(path: $navState, root: {
            map(state: state)
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
    private func map(state: GameContractGameState) -> some View {
        switch state {
        case is GameContractGameState.InMenu:
            MainMenuView(vm: menuVm)
                .navigationDestination(for: NavigationState.self) {
                    mapNavigation(path: $0)
                }
        case is GameContractGameState.InLobby:
            VStack {}
        case is GameContractGameState.InRoomCreation:
            EnterScreenView(navState: $navState, stage: .playerName)
        case is GameContractGameState.InSettings:
            VStack{}
        default:
            VStack{}
        }
    }

    @ViewBuilder
    private func mapNavigation(path: NavigationState) -> some View {
        switch path {
        case .enterName:
            EnterScreenView(navState: $navState, stage: .playerName)
                .textContentType(.name)
        case .enterCode:
            EnterScreenView(navState: $navState, stage: .roomCode)
                .textContentType(.oneTimeCode)
        case .lobby:
            LobbyView(navState: $navState)
        default:
            EmptyView()
        }
    }

    private func process(effect: MenuContractEffect) {
        // TODO: Process effects properly
        switch effect {
        case is MenuContractEffect.NavigationNewGameScreen:
            navState.append(.enterName)
        case is MenuContractEffect.NavigationJoinGameScreen:
            navState.append(.enterCode)
        default:
            break
        }
    }
}

// MARK: - Previews

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView(
            state: .constant(.InMenu()),
            vm: .init()
        )
	}
}
