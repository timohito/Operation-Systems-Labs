package com.company;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Draw {
    private FileManager fileManager;
    private Disk physDisk;
    private DrawDisk drawDisk;

    private final JFrame frame = new JFrame();
    private final JTextField textFieldName = new JTextField();
    private final JTextField textFieldFile = new JTextField();
    private final JTextField textFieldDisk = new JTextField();
    private final JTextField textFieldSector = new JTextField();
    private final JButton buttonCreateFile = new JButton("Create File");
    private final JButton buttonCreateFolder = new JButton("Create Folder");
    private final JButton buttonCopy = new JButton("Copy");
    private final JButton buttonPaste = new JButton("Paste");
    private final JButton buttonDelete = new JButton("Delete");
    private final JButton buttonMove = new JButton("Move");
    private final JButton buttonSetMemory = new JButton("Set disk cap");
    private final JLabel labelName = new JLabel("Name");
    private final JLabel labelSizeFile = new JLabel("File size");
    private final JLabel labelSizeDisk = new JLabel("Disk size");
    private final JLabel labelSizeSector = new JLabel("Sector size");
    private JTree tree;
    private DefaultMutableTreeNode treeFile;


    public Draw() {
        frame.setBounds(300, 300, 1400, 800);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(null);
        buttonCreateFile.setBounds(1200, 100, 150, 20);
        buttonCreateFolder.setBounds(1200, 130, 150, 20);
        buttonCopy.setBounds(1200, 160, 150, 20);
        buttonPaste.setBounds(1200, 190, 150, 20);
        buttonDelete.setBounds(1200, 220, 150, 20);
        buttonMove.setBounds(1200, 450, 150, 20);
        buttonSetMemory.setBounds(1200, 600, 150, 20);
        labelName.setBounds(1260, 300, 100, 20);
        textFieldName.setBounds(1200, 320, 150, 20);
        labelSizeFile.setBounds(1250, 360, 100, 20);
        textFieldFile.setBounds(1200, 380, 150, 20);
        labelSizeDisk.setBounds(1130, 500, 70, 20);
        textFieldDisk.setBounds(1200, 500, 100, 20);
        labelSizeSector.setBounds(1115, 550, 80, 20);
        textFieldSector.setBounds(1200, 550, 100, 20);
        textFieldDisk.setText("");
        textFieldSector.setText("");
        textFieldFile.setText("");
        frame.getContentPane().add(buttonCreateFile);
        frame.getContentPane().add(buttonCreateFolder);
        frame.getContentPane().add(buttonCopy);
        frame.getContentPane().add(buttonPaste);
        frame.getContentPane().add(buttonDelete);
        frame.getContentPane().add(buttonMove);
        frame.getContentPane().add(labelName);
        frame.getContentPane().add(textFieldName);
        frame.getContentPane().add(labelSizeDisk);
        frame.getContentPane().add(textFieldDisk);
        frame.getContentPane().add(labelSizeSector);
        frame.getContentPane().add(textFieldSector);
        frame.getContentPane().add(labelSizeFile);
        frame.getContentPane().add(textFieldFile);
        frame.getContentPane().add(buttonSetMemory);
        frame.repaint();
    }

    public void start() {
        buttonCreateFile.addActionListener(e -> {
            fileManager.createFile(textFieldName.getText(), false, Integer.parseInt(textFieldFile.getText()));
            startModifyTree(fileManager.getRootFile().getChild());
            frame.repaint();
        });

        buttonCreateFolder.addActionListener(e -> {
            fileManager.createFile(textFieldName.getText(), true, 1);
            startModifyTree(fileManager.getRootFile().getChild());
            frame.repaint();
        });

        buttonCopy.addActionListener(e -> fileManager.copy());

        buttonPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileManager.paste();
                startModifyTree(fileManager.getRootFile().getChild());
                frame.repaint();
            }
        });

        buttonDelete.addActionListener(e -> {
            fileManager.delete();
            startModifyTree(fileManager.getRootFile().getChild());
            frame.repaint();
        });

        buttonMove.addActionListener(e -> {
            fileManager.move();
            startModifyTree(fileManager.getRootFile().getChild());
            frame.repaint();
        });

        buttonSetMemory.addActionListener(e -> {
            textFieldDisk.setEditable(false);
            textFieldSector.setEditable(false);
            buttonSetMemory.setEnabled(false);
            buttonCreateFile.setEnabled(true);
            buttonDelete.setEnabled(true);
            buttonMove.setEnabled(true);
            buttonCopy.setEnabled(true);
            buttonCreateFolder.setEnabled(true);
            buttonPaste.setEnabled(true);
            labelName.setEnabled(true);
            physDisk = new Disk(Integer.parseInt(textFieldDisk.getText()), Integer.parseInt(textFieldSector.getText()));
            drawDisk = new DrawDisk(physDisk);
            drawDisk.setBounds(220, 10, 900, 650);
            frame.getContentPane().add(drawDisk);
            fileManager = new FileManager();
            physDisk.chooseFile(fileManager.getRootFile());
            frame.repaint();
            startModifyTree(fileManager.getRootFile().getChild());
        });
    }

    private void startModifyTree(ArrayList<FileManager> child) {
        treeFile = new DefaultMutableTreeNode(fileManager.getRootFile());
        modifyTree(treeFile, child);
        if (!Objects.isNull(tree)) {
            frame.getContentPane().remove(tree);
        }
        tree = new JTree(treeFile);
        tree.setEnabled(true);
        tree.setShowsRootHandles(true);
        tree.setBounds(0, 0, 200, 600);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                fileManager.setSelectedFile((DefaultMutableTreeNode) Objects.requireNonNull(tree.getSelectionPath()).getLastPathComponent());
                physDisk.chooseFile(fileManager.getSelected());
                frame.repaint();
            }
        });
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(tree);
        tree.updateUI();
        tree.setScrollsOnExpand(true);
    }

    private void modifyTree(DefaultMutableTreeNode treeFile, ArrayList<FileManager> child) {
        for (FileManager file : child) {
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file);
            treeFile.add(newNode);
            if (file.isFolder()) {
                modifyTree(newNode, file.getChild());
            }
        }
    }
}