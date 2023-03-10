//
//  MainMenuView.swift
//  iosApp
//
//  Created by Artem Yelizarov on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MainMenuView: View {
    @EnvironmentObject private var loadingState: LoadingState
    @State private var isLoaded = false
    @State private var isShowingButtons = false
    @State private var isShowingVersion = true

    var vm: MenuViewModel

    // MARK: - Body
    
    var body: some View {
        ZStack {
            if isShowingVersion {
                backgroundView
            }
            VStack {
                headerView
                VStack {
                    Spacer()
                        .frame(height: 64)
                    if isLoaded {
                        buttonStack
                            .opacity(isShowingButtons ? 1 : 0)
                        Spacer()
                    }
                }
                footerView
            }
            .ignoresSafeArea()
        }
        .loading(loadingState.isLoading)
        .onAppear {
            animateLocalState()
        }
    }

    // MARK: - Views

    private var headerView: some View {
        ZStack {
            Image.bgTexture
            Image.logo
                .resizable()
                .scaledToFit()
                .padding(.horizontal, 52)
                .padding([.top], isLoaded ? 0 : 100)
        }
    }

    private var backgroundView: some View {
        VStack {
            Spacer()
            Text("v1.0")
                .font(.bodyPrimary)
                .foregroundColor(.mainBlack)
            Spacer()
                .frame(height: 64)
        }
    }

    private var buttonStack: some View {
        VStack(spacing: .large) {
            PrimaryButton("Створити гру") {
                vm.onNewGameSelected()
            }
            PrimaryButton("Приєднатися") {
                vm.onJoinGameSelected()
            }
        }
        .padding([.bottom], 20)
    }

    private var footerView: some View {
        Image.bgTexture
            .frame(height: .extraLarge)
            .offset(y: isLoaded ? 0 : Size.extraLarge.rawValue)
    }
}

// MARK: - Private

extension MainMenuView {
    private func animateLocalState() {
        // This might be moved out if we decide to unify it throughout the project in the future
        let duration: CGFloat = 0.5

        withAnimation(.easeInOut(duration: duration)) {
            isLoaded = true
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + duration) {
            withAnimation(.easeOut(duration: duration)) {
                isShowingButtons = true
            }
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + duration * 2.5) {
            withAnimation(.easeIn(duration: duration)) {
                isShowingVersion = false
            }
        }
    }
}

// MARK: - Previews

struct MainMenuView_Previews: PreviewProvider {
    static var previews: some View {
        MainMenuView(vm: .init())
    }
}
