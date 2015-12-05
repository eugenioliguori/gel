package gel.component.toolbar;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 * Bottone delle toolbar
 */
public class ToolbarButton extends JButton{
	private static final long serialVersionUID = 122577479180837887L;

	public ToolbarButton(String _imagePath) throws IOException{
		super(new ImageIcon(ImageIO.read(new File(_imagePath))));
		setBorder(new EmptyBorder(0,0,0,0));
		setOpaque(false);
	}

}