import SwiftUI
import shared

struct ContentView: View {
    @State var state: GameState = GameState.InMenu()

    let vm = shared.GameViewModel()

	var body: some View {
        NavigationView {
            Main(state: state)
        }.onAppear(perform: {
            vm.state.collect{ gameState in
                guard let newState = gameState else { return }
                self.state = newState
            } onCompletion: { _ in

            }
        })
    }

    func Main(state: GameState) -> AnyView {
        switch state {
        case is GameState.InMenu: return AnyView(VStack{
            Text("New Game")
            Text("Join Game")
            Text("Settings")
            Text("Exit")
        })
        case is GameState.InLobby: return AnyView(VStack{})
        case is GameState.InRoomCreation: return AnyView(VStack{})
        case is GameState.InSettings: return AnyView(VStack{})
        default:
            return AnyView(VStack{})
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
