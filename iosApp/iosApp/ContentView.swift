import SwiftUI
import shared

struct ContentView: View {
    @Binding var state: GameContractGameState
    let vm: shared.GameViewModel

    var body: some View {
        NavigationView {
    
            map(state: state)
        }
    }

    func map(state: GameContractGameState) -> AnyView {

        switch state {
        case is GameContractGameState.InMenu: return AnyView(VStack{
            Button("New Game") {
//                vm.onNewGameClick()
            }
            Button("Join Game") {}
            Button("Settings") {}
            Button("Exit") {}
        })
        case is GameContractGameState.InLobby: return AnyView(VStack{})
        case is GameContractGameState.InRoomCreation: return AnyView(VStack{
            Text("Room Creation")
        })
        case is GameContractGameState.InSettings: return AnyView(VStack{})
        default:
            return AnyView(VStack{})
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView(state: .constant(GameContractGameState.InMenu()), vm: GameViewModel())
	}
}
