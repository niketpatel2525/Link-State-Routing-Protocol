package com.cs542.project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.cs542.project.graph.DijkstraShortestPathAlgorithm;
import com.cs542.project.graph.Vertex;
import com.cs542.project.gui.modifiedswingui.Router;

public class LinkStateUIFunctionMenu2 implements ActionListener {

	JPanel container;
	Topology topology;
	JPanel west, center, footer;
	private static DijkstraShortestPathAlgorithm dij;
	HashMap<Vertex, Vertex> routingTable;
	Router r[];
	private static boolean setFlag = false;

	public LinkStateUIFunctionMenu2() {
		topology = LinkStateUIFunctionMenu1.topology;

	}

	/* This function will give routing table window */
	private JPanel getRoutingTable(int id) {
		// TODO Auto-generated method stub
		BorderLayout b = new BorderLayout();
		JPanel body = new JPanel(b);
		Font font = new Font("Arial", Font.PLAIN, 26);
		System.out.println("Niket ID:" + id);
		JTextPane tp = new JTextPane();
		tp.setFont(font);
		String str = "Routing Table: Router-" + id + " \n";
		str += "--------------------------------------------------------\n";
		str += "I/P \t O/P\n";
		str += "----------------------------------\n";
		if (routingTable != null && routingTable.size() > 0) {
			Iterator it = routingTable.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Vertex, Vertex> pair = (Entry) it.next();
				if (pair.getKey().getvertexId().equals("" + id)) {
					str += " " + pair.getKey().getvertexId() + "\t " + "--" + "\n";
				} else {
					if (pair.getValue() == null) {
						str += " " + pair.getKey().getvertexId() + "\t " + " N/A " + "\n";
					} else
						str += " " + pair.getKey().getvertexId() + "\t " + pair.getValue().getvertexId() + "\n";
				}
			}

			tp.setText(str);

			StyledDocument doc = tp.getStyledDocument();
			System.out.println(str);
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);

			body.add(tp, BorderLayout.CENTER);
		}
		return body;
	}

	/* This function will give connection table panel */
	public JPanel getConnectionTable() {
		// TODO Auto-generated method stub
		container = new JPanel(new BorderLayout());
		container.setOpaque(true);
		container.setBackground(Color.white);
		container.setBorder(BorderFactory.createTitledBorder("Connection Table"));
		west = getButtonListPanel();

		JLabel headerLabel = new JLabel("Router's Routing Table", JLabel.CENTER);
		headerLabel.setFont(new Font("Courier", Font.BOLD, 30));

		footer = getFooter();
		container.add(headerLabel, BorderLayout.NORTH);
		container.add(west, BorderLayout.WEST);
		container.add(footer, BorderLayout.SOUTH);
		return container;
	}

	/* This function will set Button panel as per available Routers */
	private JPanel getButtonListPanel() {
		// TODO Auto-generated method stub
		int noOfRouters = topology.getRouters().size();

		// create JPanel and its Layout
		GridLayout g = new GridLayout(noOfRouters, 1);
		JPanel westButtonPanel = new JPanel(g);
		westButtonPanel.setAutoscrolls(true);
		// Create Buttons
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		r = new Router[noOfRouters];
		for (int i = 0; i < noOfRouters; i++) {
			r[i] = new Router("Router:" + i);
			r[i].setId(i);
			Font font = new Font("Arial", Font.PLAIN, 18);
			r[i].setFont(font);

			westButtonPanel.add(r[i], gc);

			r[i].addActionListener(this);

		}

		return westButtonPanel;
	}

	/* This function will return footer to the Jpanel */
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
				LinkStateGUI.mainFrame.remove(LinkStateGUI.bodyFunctionMenu2);
				LinkStateGUI.mainFrame.add(LinkStateGUI.jFunctionPanel);
				LinkStateGUI.mainFrame.revalidate();
				LinkStateGUI.mainFrame.repaint();
			}
		});

		return f;
	}

	/*
	 * This function will execute Dijkstra algo depending on button click event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Router rt = (Router) e.getSource();

		if (!topology.getRouters().get(rt.getId()).isUp()) {
			JLabel errorMessage = new JLabel("Router is down. Please restart the router.");
			errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
			showMessage(errorMessage, "Router is downw", JOptionPane.ERROR_MESSAGE);

		} else {
			if (setFlag) {
				container.remove(center);
				container.revalidate();
				container.repaint();
			}
			System.out.println("My ID:" + rt.getId());
			dij = new DijkstraShortestPathAlgorithm(topology.getG());
			dij.findPath(topology.getRouters().get(rt.getId()));
			routingTable = dij.fetchRoutingTable();

			if (routingTable.size() > 0) {

				center = getRoutingTable(rt.getId());
				center.setAutoscrolls(true);
				container.add(center, BorderLayout.CENTER);
				LinkStateGUI.mainFrame.revalidate();
				LinkStateGUI.mainFrame.repaint();
				setFlag = true;
			} else {
				JLabel errorMessage = new JLabel("Router has no out going link.");
				errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
				showMessage(errorMessage, "Connection Table does not exist", JOptionPane.ERROR_MESSAGE);
			}
		}
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
