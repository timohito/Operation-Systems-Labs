package com.company;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

public class FileManager {

    private File rootFile = new File("root", 0);
    private File selected = rootFile;
    private File fileForCopy;
    private File parent;
    private Disk physDisk;
    private String name;
    private boolean folder;
    private int size = -1;
    private int indexFirstCell;
    private ArrayList<File> child;

    public FileManager() { }

    public FileManager(String name, File parent, boolean folder, int size) {
        this.name = name;
        this.parent = parent;
        this.folder = folder;
        this.size = size;

        if (folder) {
            child = new ArrayList();
        }
    }


    public FileManager clone() throws CloneNotSupportedException {
        FileManager newFile = new FileManager();
        newFile.setSize(size);
        newFile.setName(name);
        newFile.setFolder(folder);
        if (folder) {
            ArrayList child = new ArrayList<FileManager>();
            for (File file : this.child) {
                child.add(file.clone());
            }
            newFile.setChild(child);
        }
        return newFile;
    }

    public File getRootFile() {
        return rootFile;
    }

    public File getSelected() {
        return selected;
    }

    public File copy() {
        return fileForCopy = selected;
    }

    public void setSelectedFile(DefaultMutableTreeNode node) {
        this.selected = (File) node.getUserObject();
    }

    public void copyFilesCatalog(File newFile) {
        for (File file : newFile.getChild()) {
            physDisk.search(file);
            if (file.getClass() == Catalog.class) {
                copyFilesCatalog(file);
            }
        }
    }

    public boolean paste() {
        if (selected.getClass() == Catalog.class) {
            try {
                File newFile = (File) fileForCopy.clone();
                newFile.setParent(selected);
                selected.getChild().add(newFile);
                physDisk.search(newFile);
                if (newFile.isFolder()) {
                    copyFilesCatalog(newFile);
                }
            } catch (Exception eх) {
                eх.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean createFile(String nameFile, boolean folder, int size) {
        if (selected.getClass() == Catalog.class) {

            File newFile = null;

            if (folder) {
                newFile = new Catalog(nameFile);
                newFile.setSize(1);
            } else {
                newFile = new File(nameFile, size);

            }
            physDisk.search(newFile);
            selected.getChild().add(newFile);


            DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getFileManagerTree().getLastSelectedPathComponent();
            if (node == null) {
                return false;
            }

            while (true) {
                if (nameFile == null) {
                    return false;
                } else {
                    break;
                }
            }

            while (true) {
                try {
                    size = Integer.parseInt(JOptionPane.showInputDialog(parent.frame, "Введите размер файла"));
                    if (size < physDisk.getClSize()) {
                        throw new Exception();
                    } else {
                        break;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent, "Были введены некорректные данные", "Добавление файла", JOptionPane.INFORMATION_MESSAGE);
                }
            }


            return true;


        } else {
            return false;
        }

        File newFile = new File(nameFile, size);
        return true;
    }

    public boolean delete() {
        if (selected == rootFile) {
            return false;
        } else {
            selected.getParent().getChild().remove(selected);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getFileManagerTree().getLastSelectedPathComponent();
            if (node == null || node.getParent() == null) {
                return false;
            }


            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();

            parentNode.remove(node);
            if (parentNode.getChildCount() == 0) {
                parentNode.add(new DefaultMutableTreeNode(new File("", 0)));
            }
            parent.getFileManagerTree().updateUI();
            parent.getFileManagerTree().expandPath(new TreePath(node.getPath()));

            if (selected.getClass() == Catalog.class) {
               deleteFolder(selected.getChild());
            }
            physDisk.deleteFile(selected);
        }
        return true;

    }

    public void deleteFolder(ArrayList<File> files) {
        for (File file : files) {
            if (file.getClass() == Catalog.class) {
                deleteFolder(file.getChild());
            }
            physDisk.deleteFile(file);
        }
    }

    public void move() {
        for (int i = 0; i < selected.getParent().child.size(); i++) {
            if (selected.getParent().child.get(i) == selected) {
                selected.getParent().child.remove(i);
            }
        }
    }


    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public File getParent() {
        return parent;
    }

    public void setParent(File parent) {
        this.parent = parent;
    }

    public ArrayList<File> getChild() {
        return child;
    }

    public void setChild(ArrayList child) {
        this.child = child;
    }

    public int getIndexFirstCell() {
        return indexFirstCell;
    }


    public boolean isFolder() {
        return folder;
    }
}