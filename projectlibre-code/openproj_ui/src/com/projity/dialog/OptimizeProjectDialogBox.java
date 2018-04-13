package com.projity.dialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.projity.dialog.UpdateProjectDialogBox.Form;
import com.projity.dialog.util.ComponentFactory;
import com.projity.dialog.util.ExtDateField;
import com.projity.options.CalendarOption;
import com.projity.strings.Messages;

/**
 *
 */
public class OptimizeProjectDialogBox extends AbstractDialog {
	private static final long serialVersionUID = 1L;
	public static class Form {
		Integer time;
	
		void setTime(Integer time) {
			this.time = time;
			
		}
		
		Integer getTime() {
			return this.time;
		}
		
    }	
    
    private Form form;
    JLabel label = new JLabel("Enter new directive time:");
    JTextField directiveTime = new JTextField("", 5);
    JFrame window = new JFrame("Optimize Project");
	

        
	public static OptimizeProjectDialogBox getInstance(Frame owner) {
		return new OptimizeProjectDialogBox(owner);
	}

	private OptimizeProjectDialogBox(Frame owner) {
		super(owner, Messages.getString("Optimize Project"), true); //$NON-NLS-1$
		addDocHelp("Optimize_Project");
		
			this.form = new Form();
	}
	
	protected void initControls() {

		directiveTime.setBackground(Color.WHITE);
		directiveTime.setColumns(5);
	}
	
	

	public JComponent createContentPanel() {
	
		
		FormLayout layout = new FormLayout(
		        "20dlu,3dlu,p, 3dlu,75dlu,3dlu,30dlu ", //$NON-NLS-1$
	    		  "p,1dlu,p,1dlu,p,10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p"); //$NON-NLS-1$

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(directiveTime);

		builder.add(panel);
		
		window.getContentPane().add(panel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		return builder.getPanel();
	}
	
	
	public Form getForm() {
		return form;
	}
	public Object getBean(){
		return form;
	}
}