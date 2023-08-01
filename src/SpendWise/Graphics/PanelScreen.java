package SpendWise.Graphics;

import javax.swing.JPanel;

public abstract class PanelScreen extends JPanel implements Screen {

    public abstract boolean openScreen();

    public abstract boolean closeScreen();
}
