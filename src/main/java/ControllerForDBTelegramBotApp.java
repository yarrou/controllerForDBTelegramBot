import gui.MainWindow;
import threads.MainGuiWindowsThread;


public class ControllerForDBTelegramBotApp {
    public static void main(String[] args) throws Exception {
        MainGuiWindowsThread threadMainWindow = new MainGuiWindowsThread();
        threadMainWindow.start();
    }


}
