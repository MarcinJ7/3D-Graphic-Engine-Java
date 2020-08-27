/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafa;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.stage.Screen;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Marcin
 */
public class Frame extends JFrame 
{
    JPanel panel; 
    
    public Frame()
    {
        super();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        createPanel(screenSize.width, screenSize.height);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);
    }

    private void createPanel(int width, int height) 
    {
        panel = new Panel(width, height);
    }
    
    
}
