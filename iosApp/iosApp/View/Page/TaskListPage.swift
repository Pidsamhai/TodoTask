import SwiftUI
import shared

struct TaskListPage: View {
    @EnvironmentObject var viewModel: ViewModel
    var menuClick: () -> ()
    let width: CGFloat = UIScreen.main.nativeBounds.width
    
    init(openDrawer: @escaping () -> () = { }) {
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
                
                HStack {
                    CategoryItem(
                        title: "Business",
                        color: .purple,
                        percent: 0.5
                    ).frame(maxWidth: width * 0.5)
                    CategoryItem(
                        title: "Personal",
                        color: .blue,
                        percent: 0.9
                    ).frame(maxWidth: width * 0.5)
                }
                
                Text("Today task".uppercased())
                    .font(.subheadline)
                
                List {
                    ForEach(viewModel.tasks) { task in
                        TaskItem(
                            task: task,
                            onDelete: { viewModel.delete(id: task.id) },
                            onClick: { viewModel.toggleCompleted(id: task.id) }
                        )
                    }
                }
                .listStyle(.plain)
                .animation(.spring())
                .padding(0)
                
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
