package com.cs542.project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cs542.project.graph.Edge;
import com.cs542.project.graph.Vertex;
import com.cs542.project.testcase.FileOperation;

/* This class responsible for changing Network topology.
 * FUnction such as Add router, add Edge, add  */
public class LinkStateUIFunctionMenu4 implements ActionListener {

	Topology topology;
	JPanel container;
	JPanel west, center, footer;
	JButton addEdge, addRouter, modifyEdge, changeRouter;
	private static boolean setFlag = false;

	JTextField tf1, tf2, tf3;

	public LinkStateUIFunctionMenu4() {
		topology = LinkStateUIFunctionMenu1.topology;

	}

	/* This function will change body mainframe. */
	public JPanel getContent() {
		// TODO Auto-generated method stub
		container = new JPanel(new BorderLayout());
		container.setOpaque(true);
		container.setBackground(Color.white);
		container.setBorder(BorderFactory.createTitledBorder("Modify Topology"));

		west = getWestPanel();
		footer = getFooter();

		container.add(west, BorderLayout.WEST);
		container.add(footer, BorderLayout.SOUTH);
		return container;
	}

	/*
	 * This function wil add west panel of button. which will handle main
	 * operation of this class.
	 */
	private JPanel getWestPanel() {
		// TODO Auto-generated method stub
		GridLayout g = new GridLayout(6, 1);
		JPanel result = new JPanel(g);

		// Create Buttons
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;

		addEdge = new JButton("Add Edge");

		addRouter = new JButton("Add Router");
		// addRouter.setActionCommand("Add Router");

		modifyEdge = new JButton("Modify Edge");

		changeRouter = new JButton("Change Router");

		result.add(addEdge, gc);
		result.add(addRouter, gc);
		result.add(modifyEdge, gc);
		result.add(changeRouter, gc);

		addEdge.addActionListener(this);
		addRouter.addActionListener(this);
		modifyEdge.addActionListener(this);
		changeRouter.addActionListener(this);

		return result;
	}

	/* This function will implement perticular operation on button click */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (setFlag) {
			container.remove(center);
			container.revalidate();
			container.repaint();
		}
		switch (e.getActionCommand()) {
		case "Add Edge":
			center = addEdge();
			container.add(center, BorderLayout.CENTER);
			container.revalidate();
			container.repaint();
			setFlag = true;
			break;
		case "Add Router":

			center = addRouter();
			container.add(center, BorderLayout.CENTER);
			container.revalidate();
			container.repaint();
			setFlag = true;
			break;
		case "Modify Edge":

			center = modigyEdge();
			container.add(center, BorderLayout.CENTER);
			container.revalidate();
			container.repaint();
			setFlag = true;
			break;
		case "Change Router":
			center = changeStatus();
			container.add(center, BorderLayout.CENTER);
			container.revalidate();
			container.repaint();
			setFlag = true;
			break;

		}

	}

	/* It will change status of router from working to down and vice versa. */
	private JPanel changeStatus() {
		// TODO Auto-generated method stub
		JPanel c = new JPanel(new BorderLayout());
		JLabel headerLabel = new JLabel("Change Status of Router ", JLabel.CENTER);
		headerLabel.setFont(new Font("Courier", Font.BOLD, 30));
		c.add(headerLabel, BorderLayout.NORTH);
		JPanel header = new JPanel();
		Font titleFont = new Font("Arial", Font.PLAIN, 26);

		JLabel src = new JLabel("Enter Router ID");
		src.setFont(titleFont);

		tf1 = new JTextField(5);

		header.add(src);
		header.add(tf1);

		JButton submit = new JButton("Submit");
		header.add(submit);
		c.add(header, BorderLayout.CENTER);
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int src1;
				try {
					src1 = Integer.parseInt(tf1.getText().toString());

					int availRouter = topology.getRouters().size();
					if (src1 > availRouter - 1) {
						// Add Dialog for Error ... Router Not available

						JLabel errorMessage = new JLabel(
								"Router is either not available to the current working topology or down.");
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Router is not available", JOptionPane.ERROR_MESSAGE);

						System.out.println("Router Not Available");
					} else {
						if (topology.getRouters().get(src1).isUp()) {
							topology.getRouters().get(src1).setUp(false);

							// Dialog Box--- Status DOWN

							JLabel errorMessage = new JLabel("Router:" + src1 + " is down now.");
							errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
							showMessage(errorMessage, "Router status is changed", JOptionPane.INFORMATION_MESSAGE);
							System.out.println("DOWN");
						} else {
							topology.getRouters().get(src1).setUp(true);

							// Dialog Box --- Status UP
							JLabel errorMessage = new JLabel("Router:" + src1 + " is working now.");
							errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
							showMessage(errorMessage, "Router status is changed", JOptionPane.INFORMATION_MESSAGE);
							System.out.println("UP");
						}

						// Dialog Box for Success
					}

				} catch (NumberFormatException exs) {
					System.out.println("Number Format Exception");
					// Add Dialog box for Error!!
				} finally {

				}

			}
		});

		return c;
	}

	/* This function will modify edge weight */
	private JPanel modigyEdge() {
		JPanel c = new JPanel(new BorderLayout());

		JLabel headerLabel = new JLabel(" Modify  Link Weight ", JLabel.CENTER);
		headerLabel.setFont(new Font("Courier", Font.BOLD, 30));
		c.add(headerLabel, BorderLayout.NORTH);

		JPanel header = new JPanel();
		Font titleFont = new Font("Arial", Font.PLAIN, 26);

		JLabel src = new JLabel("Source Router");
		src.setFont(titleFont);

		JLabel dst = new JLabel("Destination Router");
		dst.setFont(titleFont);

		JLabel wet = new JLabel("Weigth");
		wet.setFont(titleFont);

		tf1 = new JTextField(5);
		tf2 = new JTextField(5);
		tf3 = new JTextField(5);

		header.add(src);
		header.add(tf1);

		header.add(dst);
		header.add(tf2);

		header.add(wet);
		header.add(tf3);

		JButton submit = new JButton("Submit");
		header.add(submit);
		c.add(header, BorderLayout.CENTER);
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int src1, dst1, wet1;
				try {
					src1 = Integer.parseInt(tf1.getText().toString());
					dst1 = Integer.parseInt(tf2.getText().toString());
					wet1 = Integer.parseInt(tf3.getText().toString());
					System.out.println("" + src1 + " " + dst1 + " " + wet1);
					int availRouter = topology.getRouters().size();
					if (src1 > availRouter - 1 || dst1 > availRouter - 1) {
						// Add Dialog for Error ... Router Not available

						JLabel errorMessage = new JLabel(
								"Router is either not available to the current working topology or down.");
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Router is not available", JOptionPane.ERROR_MESSAGE);

						System.out.println("Router Not Available");
					} else {

						ArrayList<Edge> edges = topology.getLinks();
						boolean flag = false;
						for (Edge ed : edges) {

							if (wet1 >= 0) {

								// for src-->des
								if (ed.getSource().getvertexId().equals("" + src1)
										&& ed.getDestination().getvertexId().equals("" + dst1)) {
									ed.setWeight(wet1);
									flag = true;
								}

								/*
								 * // for des-->src if
								 * (ed.getSource().getvertexId().equals("" +
								 * dst1) &&
								 * ed.getDestination().getvertexId().equals("" +
								 * src1)) { ed.setWeight(wet1); flag = true; }
								 */
							} else {
								// for src-->des
								if (ed.getSource().getvertexId().equals("" + src1)
										&& ed.getDestination().getvertexId().equals("" + dst1)) {
									edges.remove(ed);
									flag = true;
								}

								/*
								 * // for des-->src if
								 * (ed.getSource().getvertexId().equals("" +
								 * dst1) &&
								 * ed.getDestination().getvertexId().equals("" +
								 * src1)) { edges.remove(ed); flag = true; }
								 */
							}
						}

						if (!flag) {
							// Show Error Link is not found...
							System.out.println("Link Not Exist");

							JLabel errorMessage = new JLabel(
									"Link is not exist  from Router:" + src1 + " to Router:" + dst1);
							errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
							showMessage(errorMessage, "Router is not available", JOptionPane.ERROR_MESSAGE);

						}
						topology.setLinks(edges);

						// Dialog Box for Success
						JLabel errorMessage = new JLabel(
								"Link is modified from Router:" + src1 + " to Router:" + dst1 + " with cost:" + wet1);
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Router is not available", JOptionPane.INFORMATION_MESSAGE);

						System.out.println("Link Chnaged");

					}

				} catch (NumberFormatException exs) {
					System.out.println("Number Format Exception");
					// Add Dialog box for Error!!
					JLabel errorMessage = new JLabel("Please provide only numbers ( greater than and equal to 0)");
					errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
					showMessage(errorMessage, "Number Format is wrong.", JOptionPane.ERROR_MESSAGE);

				} finally {

				}

			}
		});

		return c;
	}

	// This function will add router to the system.
	private JPanel addRouter() {
		// TODO Auto-generated method stub
		JPanel c = new JPanel(new BorderLayout());

		JLabel headerLabel = new JLabel("Add Router ", JLabel.CENTER);
		headerLabel.setFont(new Font("Courier", Font.BOLD, 30));
		c.add(headerLabel, BorderLayout.NORTH);

		JPanel header = new JPanel();
		Font titleFont = new Font("Arial", Font.PLAIN, 26);

		JLabel src = new JLabel("Do you want to add new Router?");
		src.setFont(titleFont);

		header.add(src);

		JButton yes = new JButton("Yes, Add Router");
		header.add(yes);
		JButton no = new JButton("No, I don't");
		header.add(no);

		c.add(header, BorderLayout.CENTER);
		yes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int lastRouter = topology.getRouters().size();
				System.out.println("SIZE1:" + lastRouter);
				Vertex v = new Vertex("" + lastRouter, false);
				System.out.println("SIZE2:" + lastRouter);
				topology.getRouters().add(v);

				// Dialog box ----- router added successfully.

				JLabel message = new JLabel("Router is added in topology.");
				message.setFont(new Font("Arial", Font.BOLD, 26));
				showMessage(message, "Router added", JOptionPane.INFORMATION_MESSAGE);

				JLabel message2 = new JLabel("Please connect it to other routers first.");
				message2.setFont(new Font("Arial", Font.BOLD, 26));
				showMessage(message2, "Router added", JOptionPane.INFORMATION_MESSAGE);

				System.out.println("Router added:" + v.getvertexId());
			}
		});

		no.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				container.remove(center);
			}
		});

		return c;
	}

	/* This function will add edge to the topology */
	private JPanel addEdge() {
		// TODO Auto-generated method stub
		JPanel c = new JPanel(new BorderLayout());
		JLabel headerLabel = new JLabel("Add Edge ", JLabel.CENTER);
		headerLabel.setFont(new Font("Courier", Font.BOLD, 30));
		c.add(headerLabel, BorderLayout.NORTH);
		JPanel header = new JPanel();
		Font titleFont = new Font("Arial", Font.PLAIN, 26);

		JLabel src = new JLabel("Source Router");
		src.setFont(titleFont);

		JLabel dst = new JLabel("Destination Router");
		dst.setFont(titleFont);

		JLabel wet = new JLabel("Weigth");
		wet.setFont(titleFont);

		tf1 = new JTextField(5);
		tf2 = new JTextField(5);
		tf3 = new JTextField(5);

		header.add(src);
		header.add(tf1);

		header.add(dst);
		header.add(tf2);

		header.add(wet);
		header.add(tf3);

		JButton submit = new JButton("Submit");
		header.add(submit);
		c.add(header, BorderLayout.CENTER);
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int src1, dst1, wet1;
				try {
					src1 = Integer.parseInt(tf1.getText().toString());
					dst1 = Integer.parseInt(tf2.getText().toString());
					wet1 = Integer.parseInt(tf3.getText().toString());
					System.out.println("" + src1 + " " + dst1 + " " + wet1);
					int availRouter = topology.getRouters().size();
					if (src1 > availRouter - 1 || dst1 > availRouter - 1) {
						// Add Dialog for Error ... Router Not available

						JLabel errorMessage = new JLabel(
								"Router is either not available to the current working topology or down.");
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Router is not available", JOptionPane.ERROR_MESSAGE);

						System.out.println("Router Not Available");
					} else {

						// make up the router to work.
						if (!topology.getRouters().get(src1).isUp())
							topology.getRouters().get(src1).setUp(true);
						if (!topology.getRouters().get(dst1).isUp())
							topology.getRouters().get(dst1).setUp(true);

						Edge edge = new Edge("" + ++FileOperation.LINK_ID, topology.getRouters().get(src1),
								topology.getRouters().get(dst1), wet1);
						topology.getLinks().add(edge);

						// for bi directional
						/*
						 * Edge edge1 = new Edge("" + ++FileOperation.LINK_ID,
						 * topology.getRouters().get(dst1),
						 * topology.getRouters().get(src1), wet1);
						 * topology.getLinks().add(edge1);
						 */

						System.out.println("Added");

						// Dialog Box for Success

						JLabel errorMessage = new JLabel(
								"Link is added from Router:" + src1 + " to Router:" + dst1 + " with cost:" + wet1);
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Router is not available", JOptionPane.INFORMATION_MESSAGE);

					}

				} catch (NumberFormatException exs) {
					System.out.println("Number Format Exception");
					// Add Dialog box for Error!!
					JLabel errorMessage = new JLabel("Please provide only numbers ( greater than and equal to 0)");
					errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
					showMessage(errorMessage, "Number Format is wrong.", JOptionPane.ERROR_MESSAGE);
				} finally {

				}

			}
		});

		return c;
	}

	/* This function will add footer for returning back to menu. */
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
				LinkStateGUI.mainFrame.remove(LinkStateGUI.bodyFunctionMenu4);
				LinkStateGUI.mainFrame.add(LinkStateGUI.jFunctionPanel);
				LinkStateGUI.mainFrame.revalidate();
				LinkStateGUI.mainFrame.repaint();
			}
		});

		return f;
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
