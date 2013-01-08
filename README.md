SimpleVaadin project

the simplest of vaadin projects

Add the vaadin.jar to the WEB-INF/lib then

public class SimplevaadinApplication extends Application {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		Window mainWindow = new Window("Simplevaadin Application");
		Label label = new Label("Greetings");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}
}
