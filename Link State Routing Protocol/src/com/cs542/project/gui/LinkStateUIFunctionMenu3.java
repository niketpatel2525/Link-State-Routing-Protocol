package com.cs542.project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.cs542.project.graph.DijkstraShortestPathAlgorithm;
import com.cs542.project.graph.Edge;
import com.cs542.project.graph.Vertex;
import com.cs542.project.testcase.FileOperation;

/*This class is represent Linkstate function 3 which is shortest path between src and dest
 *   */
public class LinkStateUIFunctionMenu3 {
	Topology topology;
	JPanel container;
	JPanel center, footer;
	JTextField tf1, tf2, tf3;
	JPanel c;

	public LinkStateUIFunctionMenu3() {
		topology = LinkStateUIFunctionMenu1.topology;

	}

	/*
	 * This function will change functionpanel content in mainframe with its own
	 * Jpanel
	 */
	public JPanel getContent() {
		// TODO Auto-generated method stub
		container = new JPanel(new BorderLayout());
		container.setOpaque(true);
		container.setBackground(Color.white);
		container.setBorder(BorderFactory.createTitledBorder("Shortest Path"));

		JLabel headerLabel = new JLabel("Enter Source  and Destination Router ID", JLabel.CENTER);
		headerLabel.setFont(new Font("Courier", Font.BOLD, 30));

		center = getCenterContent();
		footer = getFooter();

		container.add(headerLabel, BorderLayout.NORTH);
		container.add(center, BorderLayout.CENTER);
		container.add(footer, BorderLayout.SOUTH);

		return container;
	}

	/* THis function will add center content panel in container. */
	private JPanel getCenterContent() {
		// TODO Auto-generated method stub
		c = new JPanel(new BorderLayout());

		JPanel header = new JPanel();
		Font titleFont = new Font("Arial", Font.PLAIN, 26);

		JLabel src = new JLabel("Source Router");
		src.setFont(titleFont);

		JLabel dst = new JLabel("Destination Router");
		dst.setFont(titleFont);

		tf1 = new JTextField(5);
		tf2 = new JTextField(5);

		header.add(src);
		header.add(tf1);

		header.add(dst);
		header.add(tf2);

		JButton submit = new JButton("Shortest Path");
		header.add(submit);

		JButton allShortestPath = new JButton("All Shortest Path");
		header.add(allShortestPath);

		c.add(header, BorderLayout.NORTH);

		allShortestPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int src1, dst1;
				try {
					src1 = Integer.parseInt(tf1.getText().toString());
					dst1 = Integer.parseInt(tf2.getText().toString());

					int availRouter = topology.getRouters().size();
					if (src1 > availRouter - 1 || dst1 > availRouter - 1) {
						// Add Dialog for Error ... Router Not available

						JLabel errorMessage = new JLabel(
								"Router is either not available to the current working topology or down.");
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Router is not available", JOptionPane.ERROR_MESSAGE);

						System.out.println("Router Not Available");
					} else {
						DijkstraShortestPathAlgorithm dij = new DijkstraShortestPathAlgorithm(topology.getG());
						dij.findPath(topology.getRouters().get(src1));
						ArrayList<String> path = dij.giveAllShortestPath(topology.getRouters().get(dst1));
						if (path == null || path.size() <= 0) {

							// Show Dialog Box for error --- No Path Found

							JLabel errorMessage = new JLabel("No direct path available");
							errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
							showMessage(errorMessage, "No path found.", JOptionPane.ERROR_MESSAGE);

						} else {
							JPanel jp = new JPanel(new BorderLayout());
							Font font = new Font("Arial", Font.PLAIN, 26);

							JTextPane tp = new JTextPane();
							tp.setFont(font);
							int count = path.size();
							String str = "------------All Possible Paths------------\n";
							for (String s : path) {

								System.out.println();
								StringBuilder sb = new StringBuilder(s);
								sb.reverse();
								s = sb.toString();
								s = s.trim();
								s = s.replaceAll(" ", "->");

								str += s + "\n";
							}

							str += "\n------------Cost------------\n";
							str += dij.costToReach(topology.getRouters().get(dst1));
							tp.setText(str);
							StyledDocument doc = tp.getStyledDocument();
							SimpleAttributeSet ctr = new SimpleAttributeSet();
							StyleConstants.setAlignment(ctr, StyleConstants.ALIGN_CENTER);
							doc.setParagraphAttributes(0, doc.getLength(), ctr, false);

							jp.add(tp, BorderLayout.CENTER);
							c.add(jp, BorderLayout.CENTER);
							c.revalidate();
							c.repaint();

						}
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

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int src1, dst1;
				try {
					src1 = Integer.parseInt(tf1.getText().toString());
					dst1 = Integer.parseInt(tf2.getText().toString());

					int availRouter = topology.getRouters().size();
					if (src1 > availRouter - 1 || dst1 > availRouter - 1) {
						// Add Dialog for Error ... Router Not available

						JLabel errorMessage = new JLabel(
								"Router is either not available to the current working topology or down.");
						errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
						showMessage(errorMessage, "Router is not available", JOptionPane.ERROR_MESSAGE);

						System.out.println("Router Not Available");
					} else {
						DijkstraShortestPathAlgorithm dij = new DijkstraShortestPathAlgorithm(topology.getG());
						dij.findPath(topology.getRouters().get(src1));
						ArrayList<Vertex> path = dij.makeDijkstraPathFromSourceTo(topology.getRouters().get(dst1));

						if (path == null || path.size() <= 0) {
							// Show Dialog Box for error --- No Path Found
							JLabel errorMessage = new JLabel("No direct path available");
							errorMessage.setFont(new Font("Arial", Font.BOLD, 26));
							showMessage(errorMessage, "No path found.", JOptionPane.ERROR_MESSAGE);
						} else {
							JPanel jp = new JPanel(new BorderLayout());
							Font font = new Font("Arial", Font.PLAIN, 26);

							JTextPane tp = new JTextPane();
							tp.setFont(font);
							int count = path.size();
							String str = "------------Path------------\n";
							for (Vertex vertex : path) {
								if (count > 1) {
									str += vertex.getvertexId() + "->";
									System.out.print(vertex.getvertexId() + "->");
								} else {
									str += vertex.getvertexId();
									System.out.print(vertex.getvertexId());
								}
								count--;
							}

							str += "\n------------Cost------------\n";
							str += dij.costToReach(topology.getRouters().get(dst1));
							tp.setText(str);
							StyledDocument doc = tp.getStyledDocument();
							SimpleAttributeSet ctr = new SimpleAttributeSet();
							StyleConstants.setAlignment(ctr, StyleConstants.ALIGN_CENTER);
							doc.setParagraphAttributes(0, doc.getLength(), ctr, false);

							jp.add(tp, BorderLayout.CENTER);
							c.add(jp, BorderLayout.CENTER);
							c.revalidate();
							c.repaint();

						}
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

	/* This function will add footer to go back to main menu */
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
				LinkStateGUI.mainFrame.remove(LinkStateGUI.bodyFunctionMenu3);
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
