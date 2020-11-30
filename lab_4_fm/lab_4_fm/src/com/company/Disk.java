package com.company;

import java.util.ArrayList;

public class Disk {
    private int sizeDisk;
    private int sizeSector;
    private Cluster[] clusters;

    public Disk(int sizeDisc, int sizeSector) {
        this.sizeSector = sizeSector;
        this.sizeDisk = sizeDisc;
        clusters = new Cluster[sizeDisc / sizeSector];
        for (int i = 0; i < clusters.length; i++) {
            clusters[i] = new Cluster(sizeSector);
        }
    }

    public Cluster getClIndex(int index) {
        if (index > -1 && index < clusters.length) {
            return clusters[index];
        }
        return null;
    }

    public void search(File file) {
        int buf = -2;
        for (int i = 0, writeCl = 0; i < clusters.length && writeCl != file.getSize(); i++) {
            if (clusters[i].getStatus() == ClusterStatus.EMPTY) {
                clusters[i].setStatus(ClusterStatus.IN_USE);
                if (buf == -2) {
                    setIndexFirstCell(i);
                }
                else {
                    clusters[buf].setIndex(i);
                }
                buf = i;
                writeCl++;
                if (writeCl == file.getSize()) {
                    clusters[i].setIndex(-1);
                }
            }
        }
    }

    public void chooseNotChosenFile(FileManager file) {
        if (file.isFolder()) {
            for (int i = 0; i < file.getChild().size(); i++) {
                chooseNotChosenFile(file.getChild().get(i));
            }
        }
        int index = file.getIndexFirstCell();
        for (int i = 0; i < file.getSize(); i++) {
            if(index == -1) {
                break;
            }
            clusters[index].setStatus(ClusterStatus.IN_USE);
            index = clusters[index].getIndex();
        }
    }

    public void notChooseFile() {
        for (int i = 0; i < clusters.length ; i++) {
            if (clusters[i].getStatus() == ClusterStatus.IN_USE) {
                clusters[i].setStatus(ClusterStatus.FULL);
            }
        }
    }

    public void chooseFile(FileManager file) {
        notChooseFile();
        if (file.isFolder()) {
            for (int i = 0; i < file.getChild().size(); i++) {
                chooseNotChosenFile(file.getChild().get(i));
            }
        }
        int index = file.getIndexFirstCell();
        for (int i = 0; i < file.getSize(); i++) {
            if(index == -1) {
                break;
            }
            clusters[index].setStatus(ClusterStatus.IN_USE);
            index = clusters[index].getIndex();
        }
    }

    public void deleteFile(File file) {
        if (!file.isFolder()) {
            int index = file.getIndexFirstCell();
            for (int i = 0; i < file.getSize(); i++) {
                if(index == -1) {
                    continue;
                }
                clusters[index].setStatus(ClusterStatus.EMPTY);
                index = clusters[index].getIndex();
            }
        }
        if (file.isFolder()) {
            ArrayList<File> deletedChild = file.getChild();
            for (int i = 0; i < deletedChild.size(); i++) {
                deleteFile(deletedChild.get(deletedChild.size()-1));
            }
            clusters[file.getIndexFirstCell()].setStatus(ClusterStatus.EMPTY);
        }
    }

    public void setIndexFirstCell(int indexFirstCell) {
        this.indexFirstCell = indexFirstCell;
    }


    public int getClSize() {
        return clusters.length;
    }
}