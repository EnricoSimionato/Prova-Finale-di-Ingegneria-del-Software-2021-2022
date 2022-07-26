package it.polimi.ingsw.client.gui;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class ScaledResizingList {
    private List<ScaledResizingItem> scaledResizingItems;

    private class ScaledResizingItem {
        private Node parentNode;
        private Node childNode;
        private double heightRatio;
        private double widthRatio;
        private double initialXPosition;
        private double initialYPosition;
        private double xPositionRatio;
        private double yPositionRatio;

        public ScaledResizingItem(Node parentNode, Node childNode, double heightRatio, double widthRatio, double xPositionRatio, double yPositionRatio) {
            this.parentNode = parentNode;
            this.childNode = childNode;
            this.heightRatio = heightRatio;
            this.widthRatio = widthRatio;
            initialXPosition = childNode.getLayoutX();
            initialYPosition = childNode.getLayoutY();
            this.xPositionRatio = xPositionRatio;
            this.yPositionRatio = yPositionRatio;
        }

        public void resizeNode() {
            childNode.resizeRelocate(initialXPosition * xPositionRatio, initialYPosition * yPositionRatio,  parentNode.prefWidth(-1) * widthRatio, parentNode.prefHeight(-1) * heightRatio);
        }
    }

    public ScaledResizingList() {
        this.scaledResizingItems = new ArrayList<>();
    }

    public void addItem(Node parentNode, Node childNode, double heightRatio, double widthRatio, double xPositionRatio, double yPositionRatio) {
        scaledResizingItems.add(new ScaledResizingItem(parentNode, childNode, heightRatio, widthRatio, xPositionRatio, yPositionRatio));
    }
    public void resize() {
        for (ScaledResizingItem scaledResizingItem : scaledResizingItems) {
            scaledResizingItem.resizeNode();
        }
    }
}
