import javax.swing.GroupLayout;
import javax.swing.JFrame;

public class Gra extends JFrame{
	
	public PanelGry panelGry1;
	public PanelNawigacji panelNawigacji1;
	public PolaczenieZBaza baza;
	
	public Gra() {
		this.setResizable(false);
		this.setVisible(true);
		baza = new PolaczenieZBaza();
		panelGry1 = new PanelGry();
		panelNawigacji1 = new PanelNawigacji(panelGry1, baza);

		this.setBounds(0,0,1200,840);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		GroupLayout layoutGry = new GroupLayout(this.getContentPane());
		layoutGry.setHorizontalGroup(layoutGry.createSequentialGroup()
				.addComponent(panelGry1, 800, 800, 800)
				.addComponent(panelNawigacji1, 400, 400, 400));
		layoutGry.setVerticalGroup(layoutGry.createParallelGroup()
				.addComponent(panelGry1, 800, 800, 800)
				.addComponent(panelNawigacji1, 800, 800, 800));
		getContentPane().setLayout(layoutGry);
	}
}
