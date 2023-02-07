import SwiftUI
import shared

struct ContentView: View {
    @Binding var state: GameContractGameState
    let vm: shared.GameViewModel

    // MARK: - Body

    var body: some View {
        NavigationView {
    
            map(state: state)
        }
    }
}

// MARK: - Private

extension ContentView {
    private func map(state: GameContractGameState) -> AnyView {
        switch state {
        case is GameState.InMenu: return AnyView(
            MainMenuView(vm: vm)
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
}

// MARK: - Previews

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView(state: .constant(GameContractGameState.InMenu()), vm: GameViewModel())
	}
}
