package br.uefs.apriori;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

public class Interface {
	
	private JTextField txtSuporte; 
	private JTextField txtConfianca; 
	private JTextField txtCaminho;
	private JTextArea txtResult; 
	private JButton btnOpen; 
	private JButton btnProcessar; 
	private JFrame frame;
	
	
	public void init(){
		initComponents();
		initLayout();
		initFunctions();
		frame.setSize(600, 600);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	
	private void initComponents(){
		
		txtSuporte = new JTextField(); 
		txtConfianca = new JTextField();
		txtCaminho = new JTextField(); 
		txtResult = new JTextArea(); 
		btnOpen = new JButton("..."); 
		btnProcessar = new JButton("run");
		
		this.frame = new JFrame("Apriori - Lucas e Tayane");
	}
	
	private void initLayout() {

		String columns = "10dlu, 40dlu, 10dlu, 100dlu, 10dlu, 40dlu, 10dlu";
		String rows = "20dlu, 40dlu, 5dlu, 40dlu, 5dlu, 40dlu, 5dlu, 40dlu, 5dlu, 200dlu, 20dlu";

		FormLayout form = new FormLayout(columns, rows);
		PanelBuilder builder = new PanelBuilder(form);

		builder.addLabel("Suport:", CC.xy(2, 2));
		builder.add(txtSuporte, CC.xy(4, 2));
		
		builder.addLabel("Confianca:", CC.xy(2, 4));
		builder.add(txtConfianca, CC.xy(4, 4));
		builder.addLabel("Arquivo: ", CC.xy(2, 6));
		builder.add(txtCaminho, CC.xy(4, 6));
		
		builder.add(btnOpen, CC.xy(6, 6));
		
		builder.add(btnProcessar, CC.xy(6, 8));
		
		builder.add(txtResult, CC.xywh(2, 10, 5, 1));

		frame.setContentPane(builder.getPanel());
	}
	
	
	private void initFunctions(){
		
		btnOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				File qrel;
				JFileChooser chooser = new JFileChooser();
				
				int retorno = chooser.showOpenDialog(null);

				if (retorno == JFileChooser.APPROVE_OPTION) {
					qrel = chooser.getSelectedFile();
					txtCaminho.setText(qrel.getAbsolutePath());
				}
			}
		});
		
		btnProcessar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String suporte = txtSuporte.getText().trim();
				String confianca = txtConfianca.getText().trim(); 
				String url = txtCaminho.getText().trim();
				
				double sup = Double.parseDouble(suporte);
				double conf = Double.parseDouble(confianca); 
				
				AlgoritmoApriori algoritmo = new AlgoritmoApriori(sup, conf, url);
				algoritmo.init();
				String value = algoritmo.getResult();
				
				txtResult.setText(value);
			}
		});
	}
}
