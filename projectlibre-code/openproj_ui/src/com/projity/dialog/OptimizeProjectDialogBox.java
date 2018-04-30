package com.projity.dialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.projity.datatype.Hyperlink;
import com.projity.dialog.UpdateProjectDialogBox.Form;
import com.projity.dialog.util.ComponentFactory;
import com.projity.dialog.util.ExtDateField;
import com.projity.options.CalendarOption;
import com.projity.pm.task.Project;
import com.projity.strings.Messages;

import sun.tools.jar.Main;

/**
 *
 */
public class OptimizeProjectDialogBox extends AbstractDialog {
	private static final long serialVersionUID = 1L;
	public static class Form {
		String time;
	
		void setTime(String time) {
			this.time = time;
			
		}
		
		String getTime() {
			return this.time;
		}
		
    }	
    
    private Form form;
    JLabel label = new JLabel("Enter new directive time in hours:");
    JTextField directiveTime = new JTextField("", 3);
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
		bind(true);
		String[] args = {form.getTime()};
		MainOptimize.execute(args);
		super.onOk();
	}
	
	protected boolean bind(boolean get) {
		if (form == null)
			return false;
		if (get) {
			form.setTime(directiveTime.getText()); //$NON-NLS-1$
		} 
		return true;
	}
	
	

	public JComponent createContentPanel() {

		FormLayout layout = new FormLayout("200dlu:grow",
				"p,3dlu,p,2dlu");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		 
		builder.setDefaultDialogBorder();
		builder.nextLine(2);
		
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(directiveTime);
		
		directiveTime.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                char ch = e.getKeyChar();
                if(!(Character.isDigit(ch) || ch =='.')) {
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed!");
                    directiveTime.setText(" ");
                }
            }
		});	
		
		builder.add(panel);		
		return builder.getPanel();
	}
	
	
	public Form getForm() {
		return form;
	}
	public Object getBean(){
		return form;
	}


}