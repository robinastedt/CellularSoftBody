package com.astedt.robin.cellularsoftbody.console;
//Modified version of http://www.comweb.nl/java/Console/Console.html
//Original author: RJHM van den Bergh , rvdb@comweb.nl

import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.Main;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Console extends WindowAdapter implements WindowListener, ActionListener, Runnable
{
	public JFrame frame;
	public JTextArea textArea;
        public JTextField textField;
        public ConsoleKeyListener keyListener;
        private Font font;
	private Thread reader;
	private Thread reader2;
	private boolean quit;
        private ArrayList<String> previousCommands;
        private int previousCommandsIndex;
        
					
	private final PipedInputStream pin=new PipedInputStream(); 
	private final PipedInputStream pin2=new PipedInputStream(); 
        
        

	Thread errorThrower; // just for testing (Throws an Exception at this Console
	
	public Console()
	{
		// create all components and add them
                font = new Font("monospaced", Font.PLAIN, 14);
                
		frame=new JFrame(Config.TITLE_CONSOLE);
		Dimension frameSize=new Dimension(800, 600);
		int x=(int)(frameSize.width/2);
		int y=(int)(frameSize.height/2);
		frame.setBounds(x,y,frameSize.width,frameSize.height);
		textArea=new JTextArea(Config.WELCOME_MESSAGE);
		textArea.setEditable(false);
                textArea.setFont(font);
                textArea.setBackground(Color.black);
                textArea.setForeground(Color.green);
                //DefaultCaret caret = (DefaultCaret)textArea.getCaret();
                //caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                
                textField = new JTextField();
                textField.setFont(font);
                textField.setBackground(Color.black);
                textField.setForeground(Color.green);
                keyListener = new ConsoleKeyListener();
                textField.addKeyListener(keyListener);
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(new JScrollPane(textArea),BorderLayout.CENTER);
		frame.getContentPane().add(textField,BorderLayout.SOUTH);
		frame.setVisible(true);		
		
		frame.addWindowListener(this);		
		textField.addActionListener(this);
		
                previousCommands = new ArrayList<String>();
                previousCommandsIndex = 0;
		try
		{
			PipedOutputStream pout=new PipedOutputStream(this.pin);
			System.setOut(new PrintStream(pout,true)); 
		} 
		catch (java.io.IOException io)
		{
			textArea.append("Couldn't redirect STDOUT to this console\n"+io.getMessage());
                        textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		catch (SecurityException se)
		{
			textArea.append("Couldn't redirect STDOUT to this console\n"+se.getMessage());
                        textArea.setCaretPosition(textArea.getDocument().getLength());
	    } 
		
		try 
		{
			PipedOutputStream pout2=new PipedOutputStream(this.pin2);
			System.setErr(new PrintStream(pout2,true));
		} 
		catch (java.io.IOException io)
		{
			textArea.append("Couldn't redirect STDERR to this console\n"+io.getMessage());
                        textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		catch (SecurityException se)
		{
			textArea.append("Couldn't redirect STDERR to this console\n"+se.getMessage());
                        textArea.setCaretPosition(textArea.getDocument().getLength());
                } 		
			
		quit=false; // signals the Threads that they should exit
				
		// Starting two seperate threads to read from the PipedInputStreams				
		//
                
		reader=new Thread(this);
		reader.setDaemon(true);	
                reader.setName("Console Reader 1");
		reader.start();	
		//
		reader2=new Thread(this);	
		reader2.setDaemon(true);
                reader2.setName("Console Reader 2");
		reader2.start();
							
	}
	
	public synchronized void windowClosed(WindowEvent evt)
	{
		quit=true;
		this.notifyAll(); // stop all threads
		//try { reader.join(1000);pin.close();   } catch (Exception e){}		
		//try { reader2.join(1000);pin2.close(); } catch (Exception e){}
		System.exit(0);
	}		
		
	public synchronized void windowClosing(WindowEvent evt)
	{
		frame.setVisible(false); // default behaviour of JFrame	
		frame.dispose();
	}
	
	public synchronized void actionPerformed(ActionEvent evt)
	{
            if (!textField.getText().isEmpty()) {
                previousCommands.add(textField.getText());
                previousCommandsIndex = previousCommands.size();
                textArea.append("> " + textField.getText() + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
                Main.ConsoleInput(textField.getText().split(" "));
		textField.setText("");
            }
	}

	public synchronized void run()
	{
		try
		{			
			while (Thread.currentThread()==reader)
			{
				try { this.wait(100);}catch(InterruptedException ie) {}
                                
                                
				if (pin.available()!=0)
				{
					String input=this.readLine(pin);
					textArea.append(input);
                                        textArea.setCaretPosition(textArea.getDocument().getLength());
				}
				if (quit) return;
			}
                        
			while (Thread.currentThread()==reader2)
			{
				try { this.wait(100);}catch(InterruptedException ie) {}
                                
                                
				if (pin2.available()!=0)
				{
					String input=this.readLine(pin2);
					textArea.append(input);
                                        textArea.setCaretPosition(textArea.getDocument().getLength());
				}
				if (quit) return;
			}			
		} catch (Exception e)
		{
			textArea.append("\nConsole reports an Internal error.");
			textArea.append("The error is: "+e);			
                        textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		
		// just for testing (Throw a Nullpointer after 1 second)
		if (Thread.currentThread()==errorThrower)
		{
			try { this.wait(1000); }catch(InterruptedException ie){}
			throw new NullPointerException("Application test: throwing an NullPointerException It should arrive at the console");
		}

	}
	
	public synchronized String readLine(PipedInputStream in) throws IOException
	{
		String input="";
		do
		{
			int available=in.available();
			if (available==0) break;
			byte b[]=new byte[available];
			in.read(b);
			input=input+new String(b,0,b.length);														
		}while( !input.endsWith("\n") &&  !input.endsWith("\r\n") && !quit);
		return input;
	}
        
        public synchronized void Exit() {
            frame.dispose();
        }
        
        public synchronized void Clear() {
            textArea.setText(Config.WELCOME_MESSAGE);
        }
        
        public synchronized void previousCommand(int relativeIndex) {
            if (relativeIndex == -1) {
                if (previousCommandsIndex > 0) previousCommandsIndex--;
            }
            else if (relativeIndex == 1) {
                if (previousCommandsIndex < previousCommands.size()) previousCommandsIndex++;
            }
            if (previousCommandsIndex == previousCommands.size()) textField.setText("");
            else textField.setText(previousCommands.get(previousCommandsIndex));
        }
}