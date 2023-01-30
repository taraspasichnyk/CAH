import SwiftUI
import shared

struct ContentView: View {

    let platform = shared.getPlatform()


	var body: some View {
        Text("Hello, \(platform)")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
