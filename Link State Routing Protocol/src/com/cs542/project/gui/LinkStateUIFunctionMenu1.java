package com.cs542.project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.cs542.project.graph.Edge;
import com.cs542.project.graph.Vertex;
import com.cs542.project.testcase.FileOperation;

/* This class is  used to replace function panel of Mainframe with Menu 1 UI.
 * This class is responsible for finding a topology file from file and setting up topology for other functions.
 * 
 *   */
public class LinkStateUIFunctionMenu1 {
	JLabel path;
	JPanel header;
	JPanel body;
	JPanel result;
	JPanel footer;
	protected static Topology topology;

	public LinkStateUIFunctionMenu1() {
		super();
		// TODO Auto-generated constructor stub
		topology = new Topology();
	}

	/* This function will create topology for the router */
	public JPanel createTopology() {
		// TODO Auto-generated method stub

		result = new JPanel(new BorderLayout());
		result.setOpaque(true);
		result.setBackground(Color.white);
		result.setBorder(BorderFactory.createTitledBorder("Choose Topology File"));

		// Header
		header = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

		JLabel lable1 = new JLabel("Choose Network Topology File");
		Font titleFont = new Font("Arial", Font.PLAIN, 26);
		lable1.setFont(titleFont);
		path = new JLabel("no file selected");
		JButton btBrowse = new JButton("Browse");

		// body

		body = new JPanel();

		btBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("choosertitle");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					String filepath = chooser.getSelectedFile().toString();
					path.setText(filepath);

					try {
						body = getBodyPanel(filepath);
						result.add(body, BorderLayout.CENTER);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JLabel errorMessage = new JLabel("Provide valid topology matrix file to the system.");
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Connection Table", JOptionPane.ERROR_MESSAGE);

					}

				} else {
					path.setText("No file selected");
				}
			}

		});

		// footer
		footer = getFooter();

		// add component to container

		header.add(lable1);
		header.add(path);
		header.add(btBrowse);

		// add header to parent panel
		result.add(header, BorderLayout.NORTH);
		result.add(footer, BorderLayout.SOUTH);
		return result;
	}

	/* This function will add footer to to panel */
	private JPanel getFooter() {
		// TODO Auto-generated method stub
		JPanel f = new JPanel(new BorderLayout());
		ImageIcon ic = new ImageIcon("../Link State Routing Protocol/res/back.png");
		JButton exit = new JButton("Return", ic);
		Font font = new Font("Arial", Font.PLAIN, 26);
		exit.setFont(font);
		f.add(exit, BorderLayout.WEST);

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				LinkStateGUI.mainFrame.remove(LinkStateGUI.bodyFunctionMenu1);
				LinkStateGUI.mainFrame.add(LinkStateGUI.jFunctionPanel);
				LinkStateGUI.mainFrame.revalidate();
				LinkStateGUI.mainFrame.repaint();
			}
		});

		return f;
	}

	/* This function will replace content with its own content in body panels */
	private JPanel getBodyPanel(String filepath) throws Exception {
		// TODO Auto-generated method stub

		FileOperation fOps = new FileOperation();
		fOps.topology(filepath);
		int[][] data = fOps.getTopology1();
		ArrayList<Vertex> v = fOps.getNodes();
		ArrayList<Edge> e = fOps.getEdges();

		// setting topology for other functions
		topology.setG(fOps.getGraph());
		topology.setRouters(v);
		topology.setLinks(e);
		GridLayout g = new GridLayout(v.size() + 1, e.size() + 1);

		// g.setHgap(2);
		// g.setVgap(2);
		JPanel container = new JPanel(g);
		container.setOpaque(true);
		container.setBackground(Color.white);
		container.setBorder(BorderFactory.createTitledBorder("Modify Topology"));

		Font f = new Font("Arial", Font.PLAIN, 18);
		JLabel jl = new JLabel(" ");
		jl.setFont(f);
		container.add(jl);
		for (int i = 0; i < data.length; i++) {
			JLabel jlr = new JLabel("R" + i);
			jlr.setFont(f);
			container.add(jlr);
		}
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				JLabel jlc;
				if (j == 0) {
					jlc = new JLabel("R" + i);
					jlc.setFont(f);
					container.add(jlc);
				}
				jlc = new JLabel("" + data[i][j]);
				jlc.setFont(f);
				container.add(jlc);
			}
		}
		return container;
	}

	/*
	 * This function is used to display messages depending on messageTypes and
	 * Message
	 */
	private static void showMessage(Object message, String title, int messageType) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}
}
