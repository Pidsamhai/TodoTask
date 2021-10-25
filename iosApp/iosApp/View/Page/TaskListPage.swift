import SwiftUI
import shared

struct TaskListPage: View {
    @EnvironmentObject var viewModel: ViewModel
    var menuClick: () -> ()?
    
    init(openDrawer: @escaping () -> ()?) {
        self.menuClick = openDrawer
    }
    
    var body: some View {
        VStack(
            spacing: 0
        ) {
            CustomAppBar(
                navigation: {
                    Image(systemName: "equal")
                        .padding()
                        .onTapGesture {
                            menuClick()
                        }
                },
                actions: {
                    Image(systemName: "magnifyingglass")
                        .padding()
                    Image(systemName: "bell")
                        .padding()
                }
            )
            content
        }
        .background(Color.white)
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity
        )
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
    }
    
    private var content: some View {
        ZStack(
            alignment: .bottomTrailing
        ) {
            
            VStack(
                alignment: .leading,
                spacing: 20
            ){
                Text("What's up, Joy!")
                    .font(.title)
                
                Text("Category".uppercased())
                    .font(.subheadline)
                GeometryReader { gp in
                    HStack {
                        CategoryItem()
                            .frame(
                                maxWidth: gp.size.width * 0.5
                            )
                        CategoryItem()
                            .frame(
                                maxWidth: gp.size.width * 0.5
                            )
                    }
                }
                
                List {
                    ForEach(viewModel.tasks) { task in
                        HStack {
                            Image(
                                systemName: task.isCompleted ?
                                "checkmark.circle.fill" : "circle"
                            ).foregroundColor(task.isCompleted ? .green : .red)
                            Text(task.title)
                        }
                    }
                    .onDelete(perform: viewModel.delete)
                }
                .listStyle(.plain)
                
            }
            .frame(
                alignment: .topLeading
            )
            .padding(.horizontal)
            
            NavigationLink(
                destination: AddTaskPage()
            ) {
                Image(systemName: "plus")
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.blue)
                    .clipShape(Circle())
                    .shadow(
                        color: .accentColor.opacity(0.5),
                        radius: 2,
                        x: 3,
                        y: 3
                    )
                    .padding()
            }
        }
    }
}

struct TaskListPage_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            TaskListPage {
                
            }
        }
        .environmentObject(ViewModel())
    }
}
