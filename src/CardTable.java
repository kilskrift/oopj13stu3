import javax.swing.*;
import java.awt.*;

import static java.awt.Toolkit.*;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

public class CardTable extends JFrame {

    // constructor, populates JFrame w/components
    CardTable() {

        //Create and set up the window.
        super("CardTable");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {

        // calculate suitable JFrame screen position
        Dimension screenSize = getDefaultToolkit().getScreenSize();

        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
        this.setLocation(screenSize.width/4, screenSize.height/4);

        // use JFrame BorderLayout
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.LIGHT_GRAY);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        this.getContentPane().add(label);

        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);

        //Add a JPanel to contain the cards
        CardPanel panel = new CardPanel();
        this.getContentPane().add(panel);

        panel.setBackground(Color.GREEN);
        panel.setVisible(true);

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {

        // program bootstrapped by CardTable constructor
        CardTable myTable = new CardTable();
    }
}


class CardPanel extends JPanel {

    ImageIcon card = new ImageIcon("img/s1.gif");


    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillRect(50, 50, 100, 200);

        g.setColor(Color.GREEN);
        g.fillRect(100,100, 60, 60);

        g.setColor(Color.BLUE);
        g.fillOval(170, 50, 100, 40);

        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        g.setColor(Color.PINK);
        g.drawString("Hej", 190, 70);

        g.setColor(Color.RED);
        g.drawString("Hopp", 210, 100);

        g.drawImage(card.getImage(), 50, 50, this );

    }
}
