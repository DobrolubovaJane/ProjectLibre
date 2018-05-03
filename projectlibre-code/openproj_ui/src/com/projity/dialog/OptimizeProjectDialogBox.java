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

/**
 *
 */
public class OptimizeProjectDialogBox extends AbstractDialog {
	private static final long serialVersionUID = 1L;
	public static class Form {
		String time;
		String cash;
	
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
		
    }	
    
    private Form form;
    JLabel label = new JLabel("Enter new directive time in hours:");
    JLabel label2 = new JLabel("Enter max cash for project:");
    JTextField directiveTime = new JTextField("", 3);
    JTextField maxCash = new JTextField("", 3);
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
		if (directiveTime.getText().isEmpty() || maxCash.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill all fields!");
		} else {
		bind(true);
		String[] args = {form.getTime(), form.getCash()};
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
		
		builder.add(panel);
		builder.nextLine(1);
		builder.add(panel2);
		return builder.getPanel();
	}
	
	
	public Form getForm() {
		return form;
	}
	public Object getBean(){
		return form;
	}


}