package com.projity.dialog;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.projity.MainOptimize;
import com.projity.datatype.Hyperlink;

public class OptimizeProjectDialogBox extends AbstractDialog {
	private static final long serialVersionUID = 1L;
	public static class Form {
		String time;
		String cash;
		String steps;
		String hromosomes;
	
		void setTime(String time) {
			this.time = time;
			
		}
		
		String getTime() {
			return this.time;
		}
		
		void setCash(String cash) {
			this.cash = cash;
			
		}
		
		String getCash() {
			return this.cash;
		}
		
		void setSteps(String steps) {
			this.steps = steps;
			
		}
		
		String getSteps() {
			return this.steps;
		}
		
		void setHromosomes(String hromosomes) {
			this.hromosomes = hromosomes;
			
		}
		
		String getHromosomes() {
			return this.hromosomes;
		}
		
    }	
    
    private Form form;
    JLabel label = new JLabel("Enter new directive time in hours:");
    JLabel label2 = new JLabel("Enter max cash for project:");
    JLabel label3 = new JLabel("Enter count of steps:");
    JLabel label4 = new JLabel("Enter count of hromosomes:");
    JTextField directiveTime = new JTextField("", 3);
    JTextField maxCash = new JTextField("", 3);
    JTextField steps = new JTextField("", 3);
    JTextField hromosomes = new JTextField("", 3);
    Hyperlink projectUrl;
	

        
	public static OptimizeProjectDialogBox getInstance(Frame owner, Hyperlink projectUrl) {
		return new OptimizeProjectDialogBox(owner, projectUrl);
	}

	private OptimizeProjectDialogBox(Frame owner, Hyperlink projectUrl) {
		super(owner, "Optimize Project", true); //$NON-NLS-1$
		this.projectUrl = projectUrl;
		addDocHelp("Optimize_Project");
		
			this.form = new Form();
	}
	
	protected void initControls() {

		directiveTime.setBackground(Color.WHITE);
		directiveTime.setSize(10, 5);
	}
	
	@Override
	public void onOk() {
		System.out.println("Time "+ directiveTime.getText() + "   Cash "+maxCash.getText());
		if (directiveTime.getText().isEmpty() || maxCash.getText().isEmpty() || 
				steps.getText().isEmpty() || hromosomes.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill all fields!");
		} else {
		bind(true);
		String[] args = {form.getTime(), form.getCash(), form.getSteps(), form.getHromosomes()};
		MainOptimize.execute(args);
		super.onOk();
		}
	}
	
	protected boolean bind(boolean get) {
		if (form == null)
			return false;
		if (get) {
			form.setTime(directiveTime.getText()); 
			form.setCash(maxCash.getText()); 
			form.setSteps(steps.getText()); 
			form.setHromosomes(hromosomes.getText()); 
		} 
		return true;
	}
	
	

	public JComponent createContentPanel() {

		FormLayout layout = new FormLayout("300dlu:grow",
				"p,3dlu,p,2dlu");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		 
		builder.setDefaultDialogBorder();
		builder.nextLine(2);
		
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(directiveTime);
		
		
		JPanel panel2 = new JPanel();
		panel.add(label2);
		panel.add(maxCash);
		
		JPanel panel3 = new JPanel();
		panel.add(label3);
		panel.add(steps);
		
		
		JPanel panel4 = new JPanel();
		panel.add(label4);
		panel.add(hromosomes);
		
		directiveTime.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                char ch = e.getKeyChar();
                if(!(Character.isDigit(ch) || ch =='.')) {
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed!");
                    directiveTime.setText(" ");
                }
            }
		});	
		
		maxCash.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                char ch = e.getKeyChar();
                if(!(Character.isDigit(ch) || ch =='.')) {
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed!");
                    maxCash.setText(" ");
                }
            }
		});	
		
		steps.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                char ch = e.getKeyChar();
                if(!(Character.isDigit(ch))) {
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed!");
                    steps.setText(" ");
                }
            }
		});	
		
		hromosomes.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                char ch = e.getKeyChar();
                if(!(Character.isDigit(ch))) {
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed!");
                    hromosomes.setText(" ");
                }
            }
		});	
		builder.add(panel);
		builder.nextLine(1);
		builder.add(panel2);
		//builder.nextLine(1);
		builder.add(panel3);
		//builder.nextLine(3);
		builder.add(panel4);
		return builder.getPanel();
	}
	
	
	public Form getForm() {
		return form;
	}
	public Object getBean(){
		return form;
	}


}