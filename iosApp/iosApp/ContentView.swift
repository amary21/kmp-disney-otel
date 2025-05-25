import UIKit
import SwiftUI
import composeApp
import PhoneNumberKit
import Foundation

struct ComposeView: UIViewControllerRepresentable {
    
    static let phoneNumberKit = PhoneNumberUtility()

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}




