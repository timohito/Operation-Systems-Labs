package com.company;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class FileManager {

    private FileManager rootFile = new FileManager("root", null, true, 1);
    private FileManager selected = rootFile;
    private FileManager fileForCopy;
    private FileManager parent;
    public ArrayList<FileManager> child;
    private Disk physDisk;
    private String name;
    private boolean folder;
    private int indexFirstCell;
    private int size = -1;

    public FileManager() { }

    public FileManager(String name, FileManager parent, boolean folder, int size) {
        this.name = name;
        this.parent = parent;
        this.folder = folder;
        this.size = size;

        if (folder) {
            child = new ArrayList();
        }
    }


    public FileManager clone() {
        FileManager newFile = new FileManager();
        newFile.setSize(size);
        newFile.setName(name);
        newFile.setFolder(folder);
        if (folder) {
            ArrayList child = new ArrayList<FileManager>();
            for (FileManager fileManager : this.child) {
                child.add(fileManager.clone());
            }
            newFile.setChild(child);
        }
        return newFile;
    }

    public FileManager getRootFile() {
        return rootFile;
    }

    public FileManager getSelected() {
        return selected;
    }

    public FileManager copy() {
        return fileForCopy = selected;
    }

    public void setSelectedFile(DefaultMutableTreeNode node) {
        this.selected = (FileManager) node.getUserObject();
    }

    public void copyFilesCatalog(FileManager newFile) {
        for (FileManager file : newFile.getChild()) {
            physDisk.search(file);
            if (file.isFolder()) {
                copyFilesCatalog(file);
            }
        }
    }

    public boolean paste() {
        if (selected.isFolder()) {
            try {
                FileManager newFile = fileForCopy.clone();
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
        if (selected.isFolder()) {
            FileManager newFile = new FileManager(nameFile, selected, folder, size);
            if (folder) {
                newFile.setSize(1);
            } else {
                newFile.setSize(size);
            }
            physDisk.search(newFile);
            selected.getChild().add(newFile);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete() {
        if (selected == rootFile) {
            return false;
        } else {
            selected.getParent().getChild().remove(selected);
            if (selected.isFolder()) {
                deleteFolder(selected.getChild());
            }
            physDisk.deleteFile(selected);
        }
        return true;
    }

    public void deleteFolder(ArrayList<FileManager> files) {
        for (FileManager file : files) {
            if (file.isFolder()) {
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

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public FileManager getParent() {
        return parent;
    }

    public void setParent(FileManager parent) {
        this.parent = parent;
    }

    public ArrayList<FileManager> getChild() {
        return child;
    }

    public void setChild(ArrayList child) {
        this.child = child;
    }

    public int getIndexFirstCell() {
        return indexFirstCell;
    }

    public void setIndexFirstCell(int indexFirstCell) {
        this.indexFirstCell = indexFirstCell;
    }
} 