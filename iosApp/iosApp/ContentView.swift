import SwiftUI
import shared

struct ContentView: View {
    @Binding var state: GameState
    let vm: shared.GameViewModel

    var body: some View {
        NavigationView {
            map(state: state)
        }
    }

    func map(state: GameState) -> AnyView {
        switch state {
        case is GameState.InMenu: return AnyView(VStack{
            Button("New Game") {
                vm.onNewGameClick()
            }
            Button("Join Game") {}
            Button("Settings") {}
            Button("Exit") {}
        })
        case is GameState.InLobby: return AnyView(VStack{})
        case is GameState.InRoomCreation: return AnyView(VStack{
            Text("Room Creation")
        })
        case is GameState.InSettings: return AnyView(VStack{})
        default:
            return AnyView(VStack{})
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView(state: .constant(.InMenu()), vm: GameViewModel())
	}
}
