import SwiftUI
import shared

struct ContentView: View {
    @Binding var state: GameContractGameState
    let vm: GameViewModel
    let menuVm: MenuViewModel = Injector.shared.menuViewModel

    // MARK: - Body

    var body: some View {
        NavigationView {
            map(state: state)
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

    private func map(state: GameContractGameState) -> AnyView {
        switch state {
        case is GameContractGameState.InMenu: return AnyView(
            MainMenuView(vm: menuVm)
        )
        case is GameContractGameState.InLobby: return AnyView(VStack{})
        case is GameContractGameState.InRoomCreation: return AnyView(VStack{
            EnterNameView(vm: vm)
        })
        case is GameContractGameState.InSettings: return AnyView(VStack{})
        default:
            return AnyView(VStack{})
        }
    }

    private func process(effect: MenuContractEffect) {
        // TODO: Process effects properly
        switch effect {
        case is MenuContractEffect.NavigationNewGameScreen:
            state = .InRoomCreation()
        case is MenuContractEffect.NavigationJoinGameScreen:
            state = .InLobby()
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
