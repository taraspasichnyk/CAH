import SwiftUI
import shared

struct ContentView: View {

	let greet = Greeting().greet()

    let player = Greeting().getPlayer()


	var body: some View {
        Text("\(greet), \(player.name)")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
