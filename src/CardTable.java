import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.ListIterator;

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

    ArrayList<Card> cards;

    CardPanel() {

        addMouseListener(new CardPanelMouseListener(this));

        // TODO move card creation to a loop or such
        Card s1 = new Card( "img/s1.gif" );
        Card s2 = new Card( 25,25,"img/s2.gif" );
        Card s3 = new Card( 50,50,"img/s3.gif" );


        cards = new ArrayList<Card>();
        cards.add(s1);
        s2.flip();
        cards.add( s2 );

        s3.flip();
        s3.flip();
        cards.add( s3 );

        /* TODO move to unit tests
        System.out.println( "s1, 0, 0 (true): " + s1.isUnder(0,0) );
        System.out.println( "s1, 200, 200 (false): " + s1.isUnder(200,200) );
        System.out.println( "s2, 25, 25 (t): " + s2.isUnder(25,25) );
        System.out.println( "s2, 24, 25 (f): " + s2.isUnder(24,25) );
        System.out.println( "s2, 25, 24 (f): " + s2.isUnder(25,24) );
        System.out.println( "s3, 26, 26 (f): " + s3.isUnder(26,26) );
        */
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        // additional custom repaint behaviour, i.e. drawing card(s)
        // cards overdraw each other in sequential order, i.e. first drawn are on bottom

        for( Card card : cards ) {
            card.draw(g, this);
        }
    }

    public Card topCardUnderMouse( int x, int y ) {

        ListIterator reverseIterator = cards.listIterator(cards.size());

        while( reverseIterator.hasPrevious()) {

           Card card = (Card) reverseIterator.previous();
            if( card.isUnder( x, y ) ) {
                return card;
            }
        }

        return null; // didn't find any card
    }

    // internal helper class to handle mouse events
    class CardPanelMouseListener extends MouseAdapter implements MouseMotionListener {

        CardPanel myPanel;

        CardPanelMouseListener( CardPanel panel ) {
            myPanel = panel;
        }

        public void mouseClicked( MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            Card card = myPanel.topCardUnderMouse(x, y);
            if( card == null ) {
                System.out.println( "no card under mouse when clicked" );
            }
            else {
                System.out.println( "found card under mouse when clicked" );
                card.flip();
                repaint();
            }
        }

    }

}

class Card {

    public int x, y;    // TODO getters, setters
    private ImageIcon myIcon;
    private ImageIcon myFront;
    static private ImageIcon myBack = new ImageIcon( "img/b1fv.gif" );
    boolean faceUp;

    Card( int x, int y, String iconPath ) {
        this.x = x;
        this.y = y;
        this.faceUp = true;
        this.setIcon(iconPath);
        this.myIcon = myFront;
    }

    Card( String iconPath ) {
        this( 0, 0, iconPath );
    }

    public ImageIcon getIcon() {
        return this.myIcon;
    }

    public void setIcon(String iconPath) {
        myFront = new ImageIcon( iconPath ); // TODO: no error handling
    }

    public void flip() {
        this.faceUp = ! this.faceUp;
        this.myIcon = this.faceUp ? this.myFront : myBack;
    }

    public void draw( Graphics g, JPanel parentPanel ) {
       g.drawImage(myIcon.getImage(), x, y, parentPanel); // x, y relative to parentPanel
    }

    public boolean isUnder( int x, int y ) {
        return ( this.x <= x && x <= (this.x + this.myIcon.getIconWidth()) &&
                 this.y <= y && y <= (this.y + this.myIcon.getIconHeight()) );
    }

}

