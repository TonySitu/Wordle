import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        WordleView view = new WordleView();
        WordleModel model = new WordleModel();
        model.addObserver(view);

        WordleController controller = new WordleController(model, view);
        controller.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controller.setResizable(true);
        controller.setLocationRelativeTo(null);
        controller.setVisible(true);

        view.update(model, null);
    }
}