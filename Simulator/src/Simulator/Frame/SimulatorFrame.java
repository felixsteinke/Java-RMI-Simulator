/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import Simulator.Frame.Dialog.Connect.ConnectToGame;
import Simulator.Frame.Dialog.Player.CreatePlayerSettings;
import Simulator.Frame.Dialog.Message.MessageDialog;
import Simulator.Frame.Dialog.Game.CreateGameLobby;
import simulator.data.container.RaceTrack;
import java.awt.EventQueue;
import java.awt.Point;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import simulator.data.container.Player;
import simulator.data.container.PlayerDatabase;
import simulator.interfaces.Client;
import simulator.interfaces.Connection;
import simulator.interfaces.Server;

/**
 *
 * @author 82stfe1bif
 */
public class SimulatorFrame extends javax.swing.JFrame {

    private static SimulatorFrame instanceSimulatorFrame;
    public DefaultListModel chatModel = new DefaultListModel();
    public DefaultListModel consoleModel = new DefaultListModel();
    private SimulatorPanel simulatorPanel = new SimulatorPanel();
    public MessageDialog chatDialog;
    public CreatePlayerSettings playerDialog;
    public CreateGameLobby gameDialog;
    public ConnectToGame connectDialog;
    private File inputFile;
    private RaceTrack raceTrackToPlay = null;
    private RaceTrack raceTrackToUpload = null;
    public PlayerDatabase playerDatabase = new PlayerDatabase();
    public boolean connected = false;
    public boolean gameIsRunning = false;

    public Player player = new Player();
    public Client ClientImpl = new ClientImpl();
    public Client clientExported;
    public Server server;
    public Registry registry;
    public Connection connection;

    public int playerCount;
    public String gameName;
    public String gameCode;

    public synchronized static SimulatorFrame getInstance() {                           //Object kann einmal erzeugt werden
        if (instanceSimulatorFrame == null) {
            instanceSimulatorFrame = new SimulatorFrame();
            //theInstance.initComponents(); 
            //als alternative methode
        }
        return instanceSimulatorFrame;
    }

    private SimulatorFrame() {
        instanceSimulatorFrame = this;
        setExtendedState(MAXIMIZED_BOTH);
        initComponents();
        jButton_Turn.setEnabled(false);
        jLabelTurn.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane_Console = new javax.swing.JScrollPane();
        jList_Console = new javax.swing.JList<>();
        jPanel_Map = simulatorPanel;
        jPanelTurn = new javax.swing.JPanel();
        jLabelTurn = new javax.swing.JLabel();
        jButton_Turn = new javax.swing.JButton();
        jTextField_Turn = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_GetStarted = new javax.swing.JMenu();
        jMenuItem_Player = new javax.swing.JMenuItem();
        jMenuItem_CloseGame = new javax.swing.JMenuItem();
        jMenu_Connection = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_Game = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_Connect = new javax.swing.JMenuItem();
        jMenuItem_Disconnect = new javax.swing.JMenuItem();
        jMenu_CreateMap = new javax.swing.JMenu();
        jMenuItem_CreateFrame = new javax.swing.JMenuItem();
        jMenuItem_Upload = new javax.swing.JMenuItem();
        jMenuItem_GetRTList = new javax.swing.JMenuItem();
        jMenuItem_Delete = new javax.swing.JMenuItem();
        jMenu_Messenger = new javax.swing.JMenu();
        jMenuItem_ShowMess = new javax.swing.JMenuItem();
        jMenu_Help = new javax.swing.JMenu();
        jMenuItem_Guide = new javax.swing.JMenuItem();
        jMenu_Exit = new javax.swing.JMenu();
        jMenuItem_ExitNow = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulator");

        jScrollPane_Console.setBorder(javax.swing.BorderFactory.createTitledBorder("Console"));

        jList_Console.setModel(consoleModel);
        jScrollPane_Console.setViewportView(jList_Console);

        javax.swing.GroupLayout jPanel_MapLayout = new javax.swing.GroupLayout(jPanel_Map);
        jPanel_Map.setLayout(jPanel_MapLayout);
        jPanel_MapLayout.setHorizontalGroup(
            jPanel_MapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 949, Short.MAX_VALUE)
        );
        jPanel_MapLayout.setVerticalGroup(
            jPanel_MapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
        );

        jPanelTurn.setBorder(javax.swing.BorderFactory.createTitledBorder("Turn"));

        jLabelTurn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelTurn.setForeground(new java.awt.Color(255, 0, 0));
        jLabelTurn.setText("SEND TURN:");

        jButton_Turn.setAction(new ActionSendTurn());
        jButton_Turn.setText("SEND");

        jTextField_Turn.setText("5");

        javax.swing.GroupLayout jPanelTurnLayout = new javax.swing.GroupLayout(jPanelTurn);
        jPanelTurn.setLayout(jPanelTurnLayout);
        jPanelTurnLayout.setHorizontalGroup(
            jPanelTurnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTurnLayout.createSequentialGroup()
                .addGroup(jPanelTurnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_Turn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelTurnLayout.createSequentialGroup()
                        .addGroup(jPanelTurnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTurnLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jTextField_Turn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelTurnLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelTurn)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTurnLayout.setVerticalGroup(
            jPanelTurnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTurnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTurn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Turn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jButton_Turn))
        );

        jMenu_GetStarted.setText("Get Started");

        jMenuItem_Player.setAction(new ActionDialogPlayer());
        jMenuItem_Player.setText("Create Player");
        jMenu_GetStarted.add(jMenuItem_Player);

        jMenuItem_CloseGame.setAction(new ActionCloseGame());
        jMenuItem_CloseGame.setText("Close Game");
        jMenu_GetStarted.add(jMenuItem_CloseGame);

        jMenuBar1.add(jMenu_GetStarted);

        jMenu_Connection.setText("Connection");
        jMenu_Connection.add(jSeparator1);

        jMenuItem_Game.setAction(new ActionDialogGame());
        jMenuItem_Game.setText("Create Game");
        jMenu_Connection.add(jMenuItem_Game);
        jMenu_Connection.add(jSeparator2);

        jMenuItem_Connect.setAction(new ActionDialogConnect());
        jMenuItem_Connect.setText("Connect");
        jMenu_Connection.add(jMenuItem_Connect);

        jMenuItem_Disconnect.setAction(new ActionDisconnectFromGame());
        jMenuItem_Disconnect.setText("Disconnect");
        jMenu_Connection.add(jMenuItem_Disconnect);

        jMenuBar1.add(jMenu_Connection);

        jMenu_CreateMap.setText("Create Map");

        jMenuItem_CreateFrame.setAction(new Simulator.Frame.ActionDialogCreateMap());
        jMenuItem_CreateFrame.setText("Open CreationTool");
        jMenu_CreateMap.add(jMenuItem_CreateFrame);

        jMenuItem_Upload.setAction(new ActionUploadGame());
        jMenuItem_Upload.setText("Upload RaceTrack");
        jMenu_CreateMap.add(jMenuItem_Upload);

        jMenuItem_GetRTList.setText("Show RaceTracks");
        jMenu_CreateMap.add(jMenuItem_GetRTList);

        jMenuItem_Delete.setAction(new ActionDeleteUploadedGame());
        jMenuItem_Delete.setText("Delete RaceTrack");
        jMenu_CreateMap.add(jMenuItem_Delete);

        jMenuBar1.add(jMenu_CreateMap);

        jMenu_Messenger.setText("Messenger");

        jMenuItem_ShowMess.setAction(new Simulator.Frame.ActionDialogMessenger());
        jMenuItem_ShowMess.setText("Show Messenger");
        jMenu_Messenger.add(jMenuItem_ShowMess);

        jMenuBar1.add(jMenu_Messenger);

        jMenu_Help.setText("Help");

        jMenuItem_Guide.setText("Guide");
        jMenu_Help.add(jMenuItem_Guide);

        jMenuBar1.add(jMenu_Help);

        jMenu_Exit.setText("Exit");

        jMenuItem_ExitNow.setAction(new ActionExit());
        jMenuItem_ExitNow.setText("Exit now");
        jMenu_Exit.add(jMenuItem_ExitNow);

        jMenuBar1.add(jMenu_Exit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Map, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane_Console)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Map, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane_Console, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanelTurn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void connect(String gameName, String gameCode) throws RemoteException {
        clientExported = (Client) UnicastRemoteObject.exportObject(ClientImpl, 0);
        player.setConnectedClient(ClientImpl);
            
        server = connection.joinGame(ClientImpl, player, gameName, gameCode);
        player.setConnectedServer(server);
            
            connected = true;
            
            String mesg = player.username + " made it in " + gameName;
            server.sendString(mesg);
    }
    
    public int calcPort() {
        String input;
        do {
            input = JOptionPane.showInputDialog(null, "Gewünschten Port eingeben:");
            System.out.println("Input not valid!");
        } while (!input.matches("[0-9]{2,6}"));
        return Integer.valueOf(input);
    }

    public String calcIP() {
        String input;
        do {
            input = JOptionPane.showInputDialog(null, "Gewünschten IP eingeben:");
            System.out.println("Input not valid!");
        } while (!input.matches("[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}"));
        return input;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton_Turn;
    public javax.swing.JLabel jLabelTurn;
    private javax.swing.JList<String> jList_Console;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem_CloseGame;
    private javax.swing.JMenuItem jMenuItem_Connect;
    private javax.swing.JMenuItem jMenuItem_CreateFrame;
    private javax.swing.JMenuItem jMenuItem_Delete;
    private javax.swing.JMenuItem jMenuItem_Disconnect;
    private javax.swing.JMenuItem jMenuItem_ExitNow;
    private javax.swing.JMenuItem jMenuItem_Game;
    private javax.swing.JMenuItem jMenuItem_GetRTList;
    private javax.swing.JMenuItem jMenuItem_Guide;
    private javax.swing.JMenuItem jMenuItem_Player;
    private javax.swing.JMenuItem jMenuItem_ShowMess;
    private javax.swing.JMenuItem jMenuItem_Upload;
    private javax.swing.JMenu jMenu_Connection;
    private javax.swing.JMenu jMenu_CreateMap;
    private javax.swing.JMenu jMenu_Exit;
    private javax.swing.JMenu jMenu_GetStarted;
    private javax.swing.JMenu jMenu_Help;
    private javax.swing.JMenu jMenu_Messenger;
    private javax.swing.JPanel jPanelTurn;
    private javax.swing.JPanel jPanel_Map;
    private javax.swing.JScrollPane jScrollPane_Console;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    public javax.swing.JTextField jTextField_Turn;
    // End of variables declaration//GEN-END:variables

    public RaceTrack getRaceTrackToPlay() {
        return this.raceTrackToPlay;
    }

    public void setRaceTrackToPlay(RaceTrack raceTrackToPlay) {
        this.raceTrackToPlay = raceTrackToPlay;
    }

    public RaceTrack getRaceTrackToUpload() {
        return raceTrackToUpload;
    }

    public void setRaceTrackToUpload(RaceTrack raceTrackToUpload) {
        this.raceTrackToUpload = raceTrackToUpload;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File newInput) {
        File oldValue = this.inputFile;
        this.inputFile = newInput;
        firePropertyChange("Input", oldValue, this.inputFile);
    }

    public void setCsvData(File input) {
        this.raceTrackToPlay = new RaceTrack(input);
        repaint();
    }
}
