package residencias;

import javax.swing.SwingUtilities;

import residencias.ui.MainFrame;

//Main, desde aqui se llamara el MainFrame
public class App {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}