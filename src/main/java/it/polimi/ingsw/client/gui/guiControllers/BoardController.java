package it.polimi.ingsw.client.gui.guiControllers;

import it.polimi.ingsw.controller.message.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class BoardController extends GuiController {

    @FXML ImageView enemyProfessor0; @FXML ImageView enemyProfessor1; @FXML ImageView enemyProfessor2; @FXML ImageView enemyProfessor3; @FXML ImageView enemyProfessor4;
    @FXML ImageView enemyEntrance0; @FXML ImageView enemyEntrance1; @FXML ImageView enemyEntrance2; @FXML ImageView enemyEntrance3; @FXML ImageView enemyEntrance4; @FXML ImageView enemyEntrance5; @FXML ImageView enemyEntrance6; @FXML ImageView enemyEntrance7; @FXML ImageView enemyEntrance8;
    @FXML ImageView enemyStudent00; @FXML ImageView enemyStudent01; @FXML ImageView enemyStudent02; @FXML ImageView enemyStudent03; @FXML ImageView enemyStudent04; @FXML ImageView enemyStudent05; @FXML ImageView enemyStudent06; @FXML ImageView enemyStudent07; @FXML ImageView enemyStudent08; @FXML ImageView enemyStudent09;
    @FXML ImageView enemyStudent10; @FXML ImageView enemyStudent11; @FXML ImageView enemyStudent12; @FXML ImageView enemyStudent13; @FXML ImageView enemyStudent14; @FXML ImageView enemyStudent15; @FXML ImageView enemyStudent16; @FXML ImageView enemyStudent17; @FXML ImageView enemyStudent18; @FXML ImageView enemyStudent19;
    @FXML ImageView enemyStudent20; @FXML ImageView enemyStudent21; @FXML ImageView enemyStudent22; @FXML ImageView enemyStudent23; @FXML ImageView enemyStudent24; @FXML ImageView enemyStudent25; @FXML ImageView enemyStudent26; @FXML ImageView enemyStudent27; @FXML ImageView enemyStudent28; @FXML ImageView enemyStudent29;
    @FXML ImageView enemyStudent30; @FXML ImageView enemyStudent31; @FXML ImageView enemyStudent32; @FXML ImageView enemyStudent33; @FXML ImageView enemyStudent34; @FXML ImageView enemyStudent35; @FXML ImageView enemyStudent36; @FXML ImageView enemyStudent37; @FXML ImageView enemyStudent38; @FXML ImageView enemyStudent39;
    @FXML ImageView enemyStudent40; @FXML ImageView enemyStudent41; @FXML ImageView enemyStudent42; @FXML ImageView enemyStudent43; @FXML ImageView enemyStudent44; @FXML ImageView enemyStudent45; @FXML ImageView enemyStudent46; @FXML ImageView enemyStudent47; @FXML ImageView enemyStudent48; @FXML ImageView enemyStudent49;
    @FXML ImageView enemyTower0; @FXML ImageView enemyTower1; @FXML ImageView enemyTower2; @FXML ImageView enemyTower3; @FXML ImageView enemyTower4; @FXML ImageView enemyTower5; @FXML ImageView enemyTower6; @FXML ImageView enemyTower7;

    @FXML Button myEntrance0; @FXML Button myEntrance1; @FXML Button myEntrance2; @FXML Button myEntrance3; @FXML Button myEntrance4; @FXML Button myEntrance5; @FXML Button myEntrance6; @FXML Button myEntrance7; @FXML Button myEntrance8;
    @FXML ImageView myProfessor0; @FXML ImageView myProfessor1; @FXML ImageView myProfessor2; @FXML ImageView myProfessor3; @FXML ImageView myProfessor4;
    @FXML ImageView myEntranceImage0; @FXML ImageView myEntranceImage1; @FXML ImageView myEntranceImage2; @FXML ImageView myEntranceImage3; @FXML ImageView myEntranceImage4; @FXML ImageView myEntranceImage5; @FXML ImageView myEntranceImage6; @FXML ImageView myEntranceImage7; @FXML ImageView myEntranceImage8;
    @FXML ImageView studentImage00; @FXML ImageView studentImage01; @FXML ImageView studentImage02; @FXML ImageView studentImage03; @FXML ImageView studentImage04; @FXML ImageView studentImage05; @FXML ImageView studentImage06; @FXML ImageView studentImage07; @FXML ImageView studentImage08; @FXML ImageView studentImage09;
    @FXML ImageView studentImage10; @FXML ImageView studentImage11; @FXML ImageView studentImage12; @FXML ImageView studentImage13; @FXML ImageView studentImage14; @FXML ImageView studentImage15; @FXML ImageView studentImage16; @FXML ImageView studentImage17; @FXML ImageView studentImage18; @FXML ImageView studentImage19;
    @FXML ImageView studentImage20; @FXML ImageView studentImage21; @FXML ImageView studentImage22; @FXML ImageView studentImage23; @FXML ImageView studentImage24; @FXML ImageView studentImage25; @FXML ImageView studentImage26; @FXML ImageView studentImage27; @FXML ImageView studentImage28; @FXML ImageView studentImage29;
    @FXML ImageView studentImage30; @FXML ImageView studentImage31; @FXML ImageView studentImage32; @FXML ImageView studentImage33; @FXML ImageView studentImage34; @FXML ImageView studentImage35; @FXML ImageView studentImage36; @FXML ImageView studentImage37; @FXML ImageView studentImage38; @FXML ImageView studentImage39;
    @FXML ImageView studentImage40; @FXML ImageView studentImage41; @FXML ImageView studentImage42; @FXML ImageView studentImage43; @FXML ImageView studentImage44; @FXML ImageView studentImage45; @FXML ImageView studentImage46; @FXML ImageView studentImage47; @FXML ImageView studentImage48; @FXML ImageView studentImage49;
    @FXML ImageView myTower0; @FXML ImageView myTower1; @FXML ImageView myTower2; @FXML ImageView myTower3; @FXML ImageView myTower4; @FXML ImageView myTower5; @FXML ImageView myTower6; @FXML ImageView myTower7;

    @FXML AnchorPane islandPane0; @FXML AnchorPane islandPane1; @FXML AnchorPane islandPane2; @FXML AnchorPane islandPane3; @FXML AnchorPane islandPane4; @FXML AnchorPane islandPane5;    @FXML AnchorPane islandPane6; @FXML AnchorPane islandPane7; @FXML AnchorPane islandPane8; @FXML AnchorPane islandPane9; @FXML AnchorPane islandPane10; @FXML AnchorPane islandPane11;
    @FXML ImageView island0; @FXML ImageView island1; @FXML ImageView island2; @FXML ImageView island3; @FXML ImageView island4; @FXML ImageView island5; @FXML ImageView island6; @FXML ImageView island7; @FXML ImageView island8; @FXML ImageView island9; @FXML ImageView island10; @FXML ImageView island11;
    @FXML ImageView student0OnIsland0; @FXML ImageView student0OnIsland1; @FXML ImageView student0OnIsland2; @FXML ImageView student0OnIsland3; @FXML ImageView student0OnIsland4; @FXML ImageView student0OnIsland5; @FXML ImageView student0OnIsland6; @FXML ImageView student0OnIsland7; @FXML ImageView student0OnIsland8; @FXML ImageView student0OnIsland9; @FXML ImageView student0OnIsland10; @FXML ImageView student0OnIsland11;
    @FXML ImageView student1OnIsland0; @FXML ImageView student1OnIsland1; @FXML ImageView student1OnIsland2; @FXML ImageView student1OnIsland3; @FXML ImageView student1OnIsland4; @FXML ImageView student1OnIsland5; @FXML ImageView student1OnIsland6; @FXML ImageView student1OnIsland7; @FXML ImageView student1OnIsland8; @FXML ImageView student1OnIsland9; @FXML ImageView student1OnIsland10; @FXML ImageView student1OnIsland11;
    @FXML ImageView student2OnIsland0; @FXML ImageView student2OnIsland1; @FXML ImageView student2OnIsland2; @FXML ImageView student2OnIsland3; @FXML ImageView student2OnIsland4; @FXML ImageView student2OnIsland5; @FXML ImageView student2OnIsland6; @FXML ImageView student2OnIsland7; @FXML ImageView student2OnIsland8; @FXML ImageView student2OnIsland9; @FXML ImageView student2OnIsland10; @FXML ImageView student2OnIsland11;
    @FXML ImageView student3OnIsland0; @FXML ImageView student3OnIsland1; @FXML ImageView student3OnIsland2; @FXML ImageView student3OnIsland3; @FXML ImageView student3OnIsland4; @FXML ImageView student3OnIsland5; @FXML ImageView student3OnIsland6; @FXML ImageView student3OnIsland7; @FXML ImageView student3OnIsland8; @FXML ImageView student3OnIsland9; @FXML ImageView student3OnIsland10; @FXML ImageView student3OnIsland11;
    @FXML ImageView student4OnIsland0; @FXML ImageView student4OnIsland1; @FXML ImageView student4OnIsland2; @FXML ImageView student4OnIsland3; @FXML ImageView student4OnIsland4; @FXML ImageView student4OnIsland5; @FXML ImageView student4OnIsland6; @FXML ImageView student4OnIsland7; @FXML ImageView student4OnIsland8; @FXML ImageView student4OnIsland9; @FXML ImageView student4OnIsland10; @FXML ImageView student4OnIsland11;
    @FXML ImageView towerOnIsland0; @FXML ImageView towerOnIsland1; @FXML ImageView towerOnIsland2; @FXML ImageView towerOnIsland3; @FXML ImageView towerOnIsland4; @FXML ImageView towerOnIsland5; @FXML ImageView towerOnIsland6; @FXML ImageView towerOnIsland7; @FXML ImageView towerOnIsland8; @FXML ImageView towerOnIsland9; @FXML ImageView towerOnIsland10; @FXML ImageView towerOnIsland11;

    @FXML Button player2; @FXML Button player3; @FXML Button player4;

    @FXML Button assistant1; @FXML Button assistant2; @FXML Button assistant3; @FXML Button assistant4; @FXML Button assistant5; @FXML Button assistant6; @FXML Button assistant7; @FXML Button assistant8; @FXML Button assistant9;@FXML Button assistant10;
    @FXML ImageView assistant1Image; @FXML ImageView assistant2Image; @FXML ImageView assistant3Image; @FXML ImageView assistant4Image; @FXML ImageView assistant5Image; @FXML ImageView assistant6Image; @FXML ImageView assistant7Image; @FXML ImageView assistant8Image; @FXML ImageView assistant9Image; @FXML ImageView assistant10Image;

    @FXML ImageView assistantImage;
    @FXML ImageView imageStudentsCount0; @FXML ImageView imageStudentsCount1; @FXML ImageView imageStudentsCount2; @FXML ImageView imageStudentsCount3; @FXML ImageView imageStudentsCount4;
    @FXML ImageView imageTowersCount;
    @FXML Label studentsCount0; @FXML Label studentsCount1; @FXML Label studentsCount2; @FXML Label studentsCount3; @FXML Label studentsCount4;
    @FXML Label towersCount;

    @FXML ImageView enemyAssistant; @FXML ImageView myAssistant;

    @FXML ImageView character1Image; @FXML ImageView character2Image; @FXML ImageView character3Image; @FXML Button student1Character1; @FXML Button student2Character1; @FXML Button student3Character1; @FXML Button student4Character1; @FXML Button student5Character1; @FXML Button student6Character1; @FXML Button student1Character2; @FXML Button student2Character2; @FXML Button student3Character2;
    @FXML Button student4Character2; @FXML Button student5Character2; @FXML Button student6Character2;@FXML Button student1Character3; @FXML Button student2Character3; @FXML Button student3Character3; @FXML Button student4Character3; @FXML Button student5Character3; @FXML Button student6Character3;

    @FXML AnchorPane centerUpperAnchorPane;
    @FXML ImageView cloud0; @FXML ImageView cloud1; @FXML ImageView cloud2; @FXML ImageView cloud3;
    @FXML AnchorPane cloudPane0; @FXML AnchorPane cloudPane1; @FXML AnchorPane cloudPane2; @FXML AnchorPane cloudPane3;
    @FXML ImageView cloud0Student0; @FXML ImageView cloud0Student1; @FXML ImageView cloud0Student2; @FXML ImageView cloud0Student3; @FXML ImageView cloud1Student0; @FXML ImageView cloud1Student1; @FXML ImageView cloud1Student2; @FXML ImageView cloud1Student3;
    @FXML ImageView cloud2Student0; @FXML ImageView cloud2Student1; @FXML ImageView cloud2Student2; @FXML ImageView cloud2Student3; @FXML ImageView cloud3Student0; @FXML ImageView cloud3Student1; @FXML ImageView cloud3Student2; @FXML ImageView cloud3Student3;

    @FXML Label labelGameMessage; @FXML Label characterEffect; @FXML Label myName; @FXML Label enemyName;
    @FXML Button pawnColor0; @FXML Button pawnColor1; @FXML Button pawnColor2; @FXML Button pawnColor3; @FXML Button pawnColor4;
    @FXML Label coinNumber; @FXML ImageView coinImage;
    @FXML Button table0; @FXML Button table1; @FXML Button table2; @FXML Button table3; @FXML Button table4;
    @FXML ImageView coinTableImage; @FXML Label coinTableNumber;
    @FXML ImageView coinCharacter1; @FXML ImageView coinCharacter2; @FXML ImageView coinCharacter3;
    @FXML ImageView prohibition0; @FXML ImageView prohibition1; @FXML ImageView prohibition2; @FXML ImageView prohibition3; @FXML ImageView prohibition4; @FXML ImageView prohibition5; @FXML ImageView prohibition6; @FXML ImageView prohibition7; @FXML ImageView prohibition8;
    @FXML ImageView prohibition9; @FXML ImageView prohibition10; @FXML ImageView prohibition11;

    @FXML ImageView myTowerIcon;
    @FXML ImageView enemyTowerIcon;
    private Map<String, Button> myEntrance;
    private Map<String, Button> studentsCards;
    private Map<String, ImageView> myEntranceImages;
    private Map<String, ImageView> myProfessors;
    private Map<String, ImageView> myTablesImages;
    private Map<String, ImageView> myTowers;
    private Map<String, ImageView> prohibitionCard;

    private Map<String, ImageView> enemyEntrance;
    private Map<String, ImageView> enemyProfessors;
    private Map<String, ImageView> enemyTables;
    private Map<String, ImageView> enemyTowers;

    private Map<String, AnchorPane> islandPanes;
    private Map<String, ImageView> islands;
    private Map<String, ImageView> studentsOnIslands;
    private Map<String, ImageView> towersOnIslands;
    private Map<String, ImageView> imageStudentsCounts;
    private Map<String, Label> studentsCounts;


    private Map<String, ImageView> clouds;
    private Map<String, ImageView> studentsClouds;

    private GameMessage board;
    private int myPlayerId;
    private int studentMoved;
    private int enemyBoardDisplayed;
    private int state;

    private ImageView motherNature;
    private int indexLastCharacterPlayed;
    private boolean toInitialize = true;
    List<String> visibleIslands = new ArrayList<>();

    /**
     * Returns the board sent by the server
     * @return board sent by the server
     */
    public GameMessage getBoard() {
        return board;
    }

    /**
     * Sets the board which is the stuff to show
     * @param board board to show on the graphic user interface
     */
    public void setBoard(GameMessage board) {
        this.board = board;
        myPlayerId = board.getPlayerId();
    }

    /**
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myEntrance = new HashMap<>();
        myProfessors = new HashMap<>();
        myEntranceImages = new HashMap<>();
        myTablesImages = new HashMap<>();
        myTowers = new HashMap<>();
        studentsCards = new HashMap<>();

        enemyEntrance = new HashMap<>();
        enemyProfessors = new HashMap<>();
        enemyTables = new HashMap<>();
        enemyTowers = new HashMap<>();

        islandPanes = new HashMap<>();
        islands = new HashMap<>();
        studentsOnIslands = new HashMap<>();
        clouds = new HashMap<>();
        studentsClouds = new HashMap<>();
        towersOnIslands = new HashMap<>();
        imageStudentsCounts = new HashMap<>();
        studentsCounts = new HashMap<>();
        prohibitionCard = new HashMap<>();


        enemyProfessors.put("enemyProfessor0", enemyProfessor0); enemyProfessors.put("enemyProfessor1", enemyProfessor1); enemyProfessors.put("enemyProfessor2", enemyProfessor2); enemyProfessors.put("enemyProfessor3", enemyProfessor3); enemyProfessors.put("enemyProfessor4", enemyProfessor4);
        enemyEntrance.put("enemyEntrance0", enemyEntrance0); enemyEntrance.put("enemyEntrance1", enemyEntrance1); enemyEntrance.put("enemyEntrance2", enemyEntrance2); enemyEntrance.put("enemyEntrance3", enemyEntrance3); enemyEntrance.put("enemyEntrance4", enemyEntrance4); enemyEntrance.put("enemyEntrance5", enemyEntrance5); enemyEntrance.put("enemyEntrance6", enemyEntrance6); enemyEntrance.put("enemyEntrance7", enemyEntrance7); enemyEntrance.put("enemyEntrance8", enemyEntrance8);

        enemyTables.put("enemyStudent00", enemyStudent00); enemyTables.put("enemyStudent01", enemyStudent01); enemyTables.put("enemyStudent02", enemyStudent02); enemyTables.put("enemyStudent03", enemyStudent03); enemyTables.put("enemyStudent04", enemyStudent04); enemyTables.put("enemyStudent05", enemyStudent05); enemyTables.put("enemyStudent06", enemyStudent06); enemyTables.put("enemyStudent07", enemyStudent07); enemyTables.put("enemyStudent08", enemyStudent08); enemyTables.put("enemyStudent09", enemyStudent09);
        enemyTables.put("enemyStudent10", enemyStudent10); enemyTables.put("enemyStudent11", enemyStudent11); enemyTables.put("enemyStudent12", enemyStudent12); enemyTables.put("enemyStudent13", enemyStudent13); enemyTables.put("enemyStudent14", enemyStudent14); enemyTables.put("enemyStudent15", enemyStudent15); enemyTables.put("enemyStudent16", enemyStudent16); enemyTables.put("enemyStudent17", enemyStudent17); enemyTables.put("enemyStudent18", enemyStudent18); enemyTables.put("enemyStudent19", enemyStudent19);
        enemyTables.put("enemyStudent20", enemyStudent20); enemyTables.put("enemyStudent21", enemyStudent21); enemyTables.put("enemyStudent22", enemyStudent22); enemyTables.put("enemyStudent23", enemyStudent23); enemyTables.put("enemyStudent24", enemyStudent24); enemyTables.put("enemyStudent25", enemyStudent25); enemyTables.put("enemyStudent26", enemyStudent26); enemyTables.put("enemyStudent27", enemyStudent27); enemyTables.put("enemyStudent28", enemyStudent28); enemyTables.put("enemyStudent29", enemyStudent29);
        enemyTables.put("enemyStudent30", enemyStudent30); enemyTables.put("enemyStudent31", enemyStudent31); enemyTables.put("enemyStudent32", enemyStudent32); enemyTables.put("enemyStudent33", enemyStudent33); enemyTables.put("enemyStudent34", enemyStudent34); enemyTables.put("enemyStudent35", enemyStudent35); enemyTables.put("enemyStudent36", enemyStudent36); enemyTables.put("enemyStudent37", enemyStudent37); enemyTables.put("enemyStudent38", enemyStudent38); enemyTables.put("enemyStudent39", enemyStudent39);
        enemyTables.put("enemyStudent40", enemyStudent40); enemyTables.put("enemyStudent41", enemyStudent41); enemyTables.put("enemyStudent42", enemyStudent42); enemyTables.put("enemyStudent43", enemyStudent43); enemyTables.put("enemyStudent44", enemyStudent44); enemyTables.put("enemyStudent45", enemyStudent45); enemyTables.put("enemyStudent46", enemyStudent46); enemyTables.put("enemyStudent47", enemyStudent47); enemyTables.put("enemyStudent48", enemyStudent48); enemyTables.put("enemyStudent49", enemyStudent49);

        enemyTowers.put("enemyTower0", enemyTower0); enemyTowers.put("enemyTower1", enemyTower1); enemyTowers.put("enemyTower2", enemyTower2); enemyTowers.put("enemyTower3", enemyTower3); enemyTowers.put("enemyTower4", enemyTower4); enemyTowers.put("enemyTower5", enemyTower5); enemyTowers.put("enemyTower6", enemyTower6); enemyTowers.put("enemyTower7", enemyTower7);

        myProfessors.put("myProfessor0", myProfessor0); myProfessors.put("myProfessor1", myProfessor1); myProfessors.put("myProfessor2", myProfessor2); myProfessors.put("myProfessor3", myProfessor3); myProfessors.put("myProfessor4", myProfessor4);
        myEntrance.put("myEntrance0", myEntrance0); myEntrance.put("myEntrance1", myEntrance1); myEntrance.put("myEntrance2", myEntrance2); myEntrance.put("myEntrance3", myEntrance3); myEntrance.put("myEntrance4", myEntrance4); myEntrance.put("myEntrance5", myEntrance5); myEntrance.put("myEntrance6", myEntrance6); myEntrance.put("myEntrance7", myEntrance7); myEntrance.put("myEntrance8", myEntrance8);
        myEntranceImages.put("myEntranceImage0", myEntranceImage0); myEntranceImages.put("myEntranceImage1", myEntranceImage1); myEntranceImages.put("myEntranceImage2", myEntranceImage2); myEntranceImages.put("myEntranceImage3", myEntranceImage3); myEntranceImages.put("myEntranceImage4", myEntranceImage4); myEntranceImages.put("myEntranceImage5", myEntranceImage5); myEntranceImages.put("myEntranceImage6", myEntranceImage6); myEntranceImages.put("myEntranceImage7", myEntranceImage7); myEntranceImages.put("myEntranceImage8", myEntranceImage8);

        myTablesImages.put("studentImage00", studentImage00); myTablesImages.put("studentImage01", studentImage01); myTablesImages.put("studentImage02", studentImage02); myTablesImages.put("studentImage03", studentImage03); myTablesImages.put("studentImage04", studentImage04); myTablesImages.put("studentImage05", studentImage05); myTablesImages.put("studentImage06", studentImage06); myTablesImages.put("studentImage07", studentImage07); myTablesImages.put("studentImage08", studentImage08); myTablesImages.put("studentImage09", studentImage09);
        myTablesImages.put("studentImage10", studentImage10); myTablesImages.put("studentImage11", studentImage11); myTablesImages.put("studentImage12", studentImage12); myTablesImages.put("studentImage13", studentImage13); myTablesImages.put("studentImage14", studentImage14); myTablesImages.put("studentImage15", studentImage15); myTablesImages.put("studentImage16", studentImage16); myTablesImages.put("studentImage17", studentImage17); myTablesImages.put("studentImage18", studentImage18); myTablesImages.put("studentImage19", studentImage19);
        myTablesImages.put("studentImage20", studentImage20); myTablesImages.put("studentImage21", studentImage21); myTablesImages.put("studentImage22", studentImage22); myTablesImages.put("studentImage23", studentImage23); myTablesImages.put("studentImage24", studentImage24); myTablesImages.put("studentImage25", studentImage25); myTablesImages.put("studentImage26", studentImage26); myTablesImages.put("studentImage27", studentImage27); myTablesImages.put("studentImage28", studentImage28); myTablesImages.put("studentImage29", studentImage29);
        myTablesImages.put("studentImage30", studentImage30); myTablesImages.put("studentImage31", studentImage31); myTablesImages.put("studentImage32", studentImage32); myTablesImages.put("studentImage33", studentImage33); myTablesImages.put("studentImage34", studentImage34); myTablesImages.put("studentImage35", studentImage35); myTablesImages.put("studentImage36", studentImage36); myTablesImages.put("studentImage37", studentImage37); myTablesImages.put("studentImage38", studentImage38); myTablesImages.put("studentImage39", studentImage39);
        myTablesImages.put("studentImage40", studentImage40); myTablesImages.put("studentImage41", studentImage41); myTablesImages.put("studentImage42", studentImage42); myTablesImages.put("studentImage43", studentImage43); myTablesImages.put("studentImage44", studentImage44); myTablesImages.put("studentImage45", studentImage45); myTablesImages.put("studentImage46", studentImage46); myTablesImages.put("studentImage47", studentImage47); myTablesImages.put("studentImage48", studentImage48); myTablesImages.put("studentImage49", studentImage49);

        myTowers.put("myTower0", myTower0); myTowers.put("myTower1", myTower1); myTowers.put("myTower2", myTower2); myTowers.put("myTower3", myTower3); myTowers.put("myTower4", myTower4); myTowers.put("myTower5", myTower5); myTowers.put("myTower6", myTower6); myTowers.put("myTower7", myTower7);

        islands.put("island0", island0); islands.put("island1", island1); islands.put("island2", island2); islands.put("island3", island3); islands.put("island4", island4); islands.put("island5", island5); islands.put("island6", island6); islands.put("island7", island7); islands.put("island8", island8); islands.put("island9", island9); islands.put("island10", island10); islands.put("island11", island11);
        islandPanes.put("islandPane0", islandPane0); islandPanes.put("islandPane1", islandPane1); islandPanes.put("islandPane2", islandPane2); islandPanes.put("islandPane3", islandPane3); islandPanes.put("islandPane4", islandPane4); islandPanes.put("islandPane5", islandPane5); islandPanes.put("islandPane6", islandPane6); islandPanes.put("islandPane7", islandPane7); islandPanes.put("islandPane8", islandPane8); islandPanes.put("islandPane9", islandPane9); islandPanes.put("islandPane10", islandPane10); islandPanes.put("islandPane11", islandPane11);
        studentsOnIslands.put("student0OnIsland0", student0OnIsland0);  studentsOnIslands.put("student0OnIsland1", student0OnIsland1);  studentsOnIslands.put("student0OnIsland2", student0OnIsland2);  studentsOnIslands.put("student0OnIsland3", student0OnIsland3);  studentsOnIslands.put("student0OnIsland4", student0OnIsland4);  studentsOnIslands.put("student0OnIsland5", student0OnIsland5);  studentsOnIslands.put("student0OnIsland6", student0OnIsland6);  studentsOnIslands.put("student0OnIsland7", student0OnIsland7);  studentsOnIslands.put("student0OnIsland8", student0OnIsland8);  studentsOnIslands.put("student0OnIsland9", student0OnIsland9);  studentsOnIslands.put("student0OnIsland10", student0OnIsland10);  studentsOnIslands.put("student0OnIsland11", student0OnIsland11);
        studentsOnIslands.put("student1OnIsland0", student1OnIsland0);  studentsOnIslands.put("student1OnIsland1", student1OnIsland1);  studentsOnIslands.put("student1OnIsland2", student1OnIsland2);  studentsOnIslands.put("student1OnIsland3", student1OnIsland3);  studentsOnIslands.put("student1OnIsland4", student1OnIsland4);  studentsOnIslands.put("student1OnIsland5", student1OnIsland5);  studentsOnIslands.put("student1OnIsland6", student1OnIsland6);  studentsOnIslands.put("student1OnIsland7", student1OnIsland7);  studentsOnIslands.put("student1OnIsland8", student1OnIsland8);  studentsOnIslands.put("student1OnIsland9", student1OnIsland9);  studentsOnIslands.put("student1OnIsland10", student1OnIsland10);  studentsOnIslands.put("student1OnIsland11", student1OnIsland11);
        studentsOnIslands.put("student2OnIsland0", student2OnIsland0);  studentsOnIslands.put("student2OnIsland1", student2OnIsland1);  studentsOnIslands.put("student2OnIsland2", student2OnIsland2);  studentsOnIslands.put("student2OnIsland3", student2OnIsland3);  studentsOnIslands.put("student2OnIsland4", student2OnIsland4);  studentsOnIslands.put("student2OnIsland5", student2OnIsland5);  studentsOnIslands.put("student2OnIsland6", student2OnIsland6);  studentsOnIslands.put("student2OnIsland7", student2OnIsland7);  studentsOnIslands.put("student2OnIsland8", student2OnIsland8);  studentsOnIslands.put("student2OnIsland9", student2OnIsland9);  studentsOnIslands.put("student2OnIsland10", student2OnIsland10);  studentsOnIslands.put("student2OnIsland11", student2OnIsland11);
        studentsOnIslands.put("student3OnIsland0", student3OnIsland0);  studentsOnIslands.put("student3OnIsland1", student3OnIsland1);  studentsOnIslands.put("student3OnIsland2", student3OnIsland2);  studentsOnIslands.put("student3OnIsland3", student3OnIsland3);  studentsOnIslands.put("student3OnIsland4", student3OnIsland4);  studentsOnIslands.put("student3OnIsland5", student3OnIsland5);  studentsOnIslands.put("student3OnIsland6", student3OnIsland6);  studentsOnIslands.put("student3OnIsland7", student3OnIsland7);  studentsOnIslands.put("student3OnIsland8", student3OnIsland8);  studentsOnIslands.put("student3OnIsland9", student3OnIsland9);  studentsOnIslands.put("student3OnIsland10", student3OnIsland10);  studentsOnIslands.put("student3OnIsland11", student3OnIsland11);
        studentsOnIslands.put("student4OnIsland0", student4OnIsland0);  studentsOnIslands.put("student4OnIsland1", student4OnIsland1);  studentsOnIslands.put("student4OnIsland2", student4OnIsland2);  studentsOnIslands.put("student4OnIsland3", student4OnIsland3);  studentsOnIslands.put("student4OnIsland4", student4OnIsland4);  studentsOnIslands.put("student4OnIsland5", student4OnIsland5);  studentsOnIslands.put("student4OnIsland6", student4OnIsland6);  studentsOnIslands.put("student4OnIsland7", student4OnIsland7);  studentsOnIslands.put("student4OnIsland8", student4OnIsland8);  studentsOnIslands.put("student4OnIsland9", student4OnIsland9);  studentsOnIslands.put("student4OnIsland10", student4OnIsland10);  studentsOnIslands.put("student4OnIsland11", student4OnIsland11);
        towersOnIslands.put("towerOnIsland0", towerOnIsland0); towersOnIslands.put("towerOnIsland1", towerOnIsland1); towersOnIslands.put("towerOnIsland2", towerOnIsland2); towersOnIslands.put("towerOnIsland3", towerOnIsland3); towersOnIslands.put("towerOnIsland4", towerOnIsland4); towersOnIslands.put("towerOnIsland5", towerOnIsland5); towersOnIslands.put("towerOnIsland6", towerOnIsland6); towersOnIslands.put("towerOnIsland7", towerOnIsland7); towersOnIslands.put("towerOnIsland8", towerOnIsland8); towersOnIslands.put("towerOnIsland9", towerOnIsland9); towersOnIslands.put("towerOnIsland10", towerOnIsland10); towersOnIslands.put("towerOnIsland11", towerOnIsland11);
        imageStudentsCounts.put("imageStudentsCount0", imageStudentsCount0); imageStudentsCounts.put("imageStudentsCount1", imageStudentsCount1); imageStudentsCounts.put("imageStudentsCount2", imageStudentsCount2); imageStudentsCounts.put("imageStudentsCount3", imageStudentsCount3); imageStudentsCounts.put("imageStudentsCount4", imageStudentsCount4);
        studentsCounts.put("studentsCount0", studentsCount0); studentsCounts.put("studentsCount1", studentsCount1); studentsCounts.put("studentsCount2", studentsCount2); studentsCounts.put("studentsCount3", studentsCount3); studentsCounts.put("studentsCount4", studentsCount4);

        studentsCards.put("student1Character1", student1Character1); studentsCards.put("student2Character1", student2Character1); studentsCards.put("student3Character1", student3Character1); studentsCards.put("student4Character1", student4Character1); studentsCards.put("student5Character1", student5Character1); studentsCards.put("student6Character1", student6Character1);
        studentsCards.put("student1Character2", student1Character2); studentsCards.put("student2Character2", student2Character2); studentsCards.put("student3Character2", student3Character2); studentsCards.put("student4Character2", student4Character2); studentsCards.put("student5Character2", student5Character2); studentsCards.put("student6Character2", student6Character2);
        studentsCards.put("student1Character3", student1Character3); studentsCards.put("student2Character3", student2Character3); studentsCards.put("student3Character3", student3Character3); studentsCards.put("student4Character3", student4Character3); studentsCards.put("student5Character3", student5Character3); studentsCards.put("student6Character3", student6Character3);

        clouds.put("cloud0", cloud0); clouds.put("cloud1", cloud1); clouds.put("cloud2", cloud2); clouds.put("cloud3", cloud3);
        studentsClouds.put("cloud0Student0", cloud0Student0); studentsClouds.put("cloud0Student1", cloud0Student1); studentsClouds.put("cloud0Student2", cloud0Student2); studentsClouds.put("cloud0Student3", cloud0Student3); studentsClouds.put("cloud1Student0", cloud1Student0); studentsClouds.put("cloud1Student1", cloud1Student1); studentsClouds.put("cloud1Student2", cloud1Student2); studentsClouds.put("cloud1Student3", cloud1Student3);
        studentsClouds.put("cloud2Student0", cloud2Student0); studentsClouds.put("cloud2Student1", cloud2Student1); studentsClouds.put("cloud2Student2", cloud2Student2); studentsClouds.put("cloud2Student3", cloud2Student3); studentsClouds.put("cloud3Student0", cloud3Student0); studentsClouds.put("cloud3Student1", cloud3Student1); studentsClouds.put("cloud3Student2", cloud3Student2);
        prohibitionCard.put("prohibition0", prohibition0); prohibitionCard.put("prohibition1", prohibition1); prohibitionCard.put("prohibition2", prohibition2); prohibitionCard.put("prohibition3", prohibition3); prohibitionCard.put("prohibition4", prohibition4); prohibitionCard.put("prohibition5", prohibition5); prohibitionCard.put("prohibition6", prohibition6);
        prohibitionCard.put("prohibition7", prohibition7); prohibitionCard.put("prohibition8", prohibition8); prohibitionCard.put("prohibition9", prohibition9); prohibitionCard.put("prohibition10", prohibition10); prohibitionCard.put("prohibition11", prohibition11);

        for(int i = 0; i < 12; i++)
            visibleIslands.add(String.valueOf(i));
        indexLastCharacterPlayed = 0;
        enemyBoardDisplayed = 1;
        studentMoved = -1;
    }

    /**
     * adapt the clouds to the screen size to make them correctly placed
     */
    private void adaptCloud() {
        cloud0Student0.setLayoutX(6.0);
        cloud0Student0.setLayoutY(45.0);
        cloud0Student1.setLayoutX(33.0);
        cloud0Student1.setLayoutY(20.0);
        cloud0Student2.setLayoutX(58.0);
        cloud0Student2.setLayoutY(45.0);

        cloud1Student0.setLayoutX(6.0);
        cloud1Student0.setLayoutY(45.0);
        cloud1Student1.setLayoutX(33.0);
        cloud1Student1.setLayoutY(20.0);
        cloud1Student2.setLayoutX(58.0);
        cloud1Student2.setLayoutY(45.0);

        cloud2Student0.setLayoutX(6.0);
        cloud2Student0.setLayoutY(45.0);
        cloud2Student1.setLayoutX(33.0);
        cloud2Student1.setLayoutY(20.0);
        cloud2Student2.setLayoutX(58.0);
        cloud2Student2.setLayoutY(45.0);
    }

    /**
     * adapt the towers to the screen size to make them correctly placed
     */
    private void adaptTowers() {
        for (int i = 0; i < 6; i++) {
            myTowers.get("myTower" + i).setLayoutX(myTowers.get("myTower" + i).getLayoutX() + 20.0);
            enemyTowers.get("enemyTower" + i).setLayoutX(enemyTowers.get("enemyTower" + i).getLayoutX() + 20.0);
        }
        myTowers.get("myTower6").setVisible(false);
        myTowers.get("myTower7").setVisible(false);
        enemyTowers.get("enemyTower6").setVisible(false);
        enemyTowers.get("enemyTower7").setVisible(false);
    }

    /**
     * adapt the scene to the screen size
     */
    public void adaptSceneToPlayers() {
        Platform.runLater(() -> {
                if (board.getNumberOfPLayers() == 2) {
                    enemyName.setVisible(true);
                    enemyName.setText(board.getPlayersNicknames()[(myPlayerId + 1) % 2]);
                    player2.setVisible(false);
                    player3.setVisible(false);
                    player4.setVisible(false);
                    enemyEntrance7.setVisible(false);
                    enemyEntrance8.setVisible(false);
                    myEntrance7.setVisible(false);
                    myEntrance8.setVisible(false);
                    cloud2.setVisible(false);
                    cloud3.setVisible(false);
                    cloudPane2.setVisible(false);
                    cloudPane3.setVisible(false);
                    for (int i = 0; i < 4; i++)
                        studentsClouds.get("cloud2Student" + i).setVisible(false);
                    for (int i = 0; i < 3; i++)
                        studentsClouds.get("cloud3Student" + i).setVisible(false);
                    cloudPane0.setTranslateX(100);
                    cloudPane1.setTranslateX(100);
                } else if (board.getNumberOfPLayers() == 3) {
                    player2.setText(board.getPlayersNicknames()[(myPlayerId + 1) % 3]);
                    player2.setPrefWidth(125);
                    player3.setText(board.getPlayersNicknames()[(myPlayerId + 2) % 3]);
                    player3.setPrefWidth(125);
                    player3.setTranslateX(45);
                    player4.setVisible(false);
                    cloud3.setVisible(false);
                    cloudPane3.setVisible(false);
                    cloudPane0.setTranslateX(50);
                    cloudPane1.setTranslateX(50);
                    cloudPane2.setTranslateX(50);
                    for (int i = 0; i < 3; i++)
                        studentsClouds.get("cloud3Student" + i).setVisible(false);
                    centerUpperAnchorPane.setPrefWidth(300);
                    adaptCloud();
                    adaptTowers();
                } else if (board.getNumberOfPLayers() == 4) {
                    player2.setText(board.getPlayersNicknames()[(myPlayerId + 1) % 4]);
                    player3.setText(board.getPlayersNicknames()[(myPlayerId + 2) % 4]);
                    player4.setText(board.getPlayersNicknames()[(myPlayerId + 3) % 4]);
                    enemyEntrance7.setVisible(false);
                    enemyEntrance8.setVisible(false);
                    myEntrance7.setVisible(false);
                    myEntrance8.setVisible(false);
                }
                myName.setText(board.getPlayersNicknames()[myPlayerId]);
        });
        motherNature = new ImageView(new Image("images/Board/Islands/motherNature.png", 25, 25,  true, false));
        displayBoard(1);
        String pane = "islandPane" + getIndexForIsland(board.getGametable().getIslands().get(board.getGametable().getMotherNaturePosition()));
        islandPanes.get(pane).getChildren().add(motherNature);
        motherNature.setX(50);
        motherNature.setY(40);
        toInitialize = false;
    }

    /**
     * Sets the student image on the node selecting the color of the student passed as parameter
     * @param node
     * @param student
     */
    public void setStudent(ImageView node, Student student) {
        Platform.runLater(() -> {
            if(node != null) {
                if (student != null) {
                    switch (student.getColor()) {
                        case GREEN:
                            node.setImage(new Image("/images/Board/Schoolboards/Students/green.png"));
                            break;
                        case RED:
                            node.setImage(new Image("/images/Board/Schoolboards/Students/red.png"));
                            break;
                        case YELLOW:
                            node.setImage(new Image("/images/Board/Schoolboards/Students/yellow.png"));
                            break;
                        case PINK:
                            node.setImage(new Image("/images/Board/Schoolboards/Students/pink.png"));
                            break;
                        case BLUE:
                            node.setImage(new Image("/images/Board/Schoolboards/Students/blue.png"));
                            break;
                    }
                } else
                    node.setImage(new Image("/images/Board/Schoolboards/circle.png"));
                node.setFitHeight(30.0);
                node.setFitWidth(30.0);
                node.setPreserveRatio(true);
            }
        });
    }

    /**
     * Sets the professor image on the node selecting the color passed as parameter
     * @param node
     * @param color
     */
    public void setProfessor(ImageView node, PawnColor color) {
        Platform.runLater(() -> {
            double x = 0;
            if(node != null) {
                if (color != null) {
                    switch (color) {
                        case GREEN:
                            node.setImage(new Image("/images/Board/Schoolboards/Professors/green.png"));
                            x = 3.0;
                            break;
                        case RED:
                            node.setImage(new Image("/images/Board/Schoolboards/Professors/red.png"));
                            x = 46.0;
                            break;
                        case YELLOW:
                            node.setImage(new Image("/images/Board/Schoolboards/Professors/yellow.png"));
                            x = 89.0;
                            break;
                        case PINK:
                            node.setImage(new Image("/images/Board/Schoolboards/Professors/pink.png"));
                            x = 132.0;
                            break;
                        case BLUE:
                            node.setImage(new Image("/images/Board/Schoolboards/Professors/blue.png"));
                            x = 175.0;
                            break;
                    }
                    node.setFitHeight(90.0);
                    node.setFitWidth(90.0);
                    node.setLayoutX(x);
                    node.setLayoutY(129.0);
                } else {
                    node.setImage(new Image("/images/Board/Schoolboards/circle.png"));
                    node.setFitHeight(30.0);
                    node.setFitWidth(30.0);
                }
                node.setPreserveRatio(true);
            }
        });
    }

    /**
     * Sets the tower image on the node selecting the color passed as parameter
     * @param node
     * @param color
     */
    public void setTower(ImageView node, TowerColor color) {
        Platform.runLater(() -> {
            if(node != null) {
                if (color != null) {
                    switch (color) {
                        case WHITE:
                            node.setImage(new Image("/images/Board/Schoolboards/Towers/whiteTower.png"));
                            break;
                        case BLACK:
                            node.setImage(new Image("/images/Board/Schoolboards/Towers/blackTower.png"));
                            break;
                        case GREY:
                            node.setImage(new Image("/images/Board/Schoolboards/Towers/greyTower.png"));
                            break;
                    }
                    node.setFitHeight(40);
                    node.setFitWidth(40);
                } else {
                    node.setImage(new Image("/images/Board/Schoolboards/circle.png"));
                    node.setFitHeight(30);
                    node.setFitWidth(30);
                }
                node.setPreserveRatio(true);
            }
        });
    }

    public void displayEnemySchoolboard() {
        displayEnemySchoolboard(enemyBoardDisplayed);
    }

    /**
     * displays the schoolboards of the other players in the left part of the screen
     * @param playerOffset
     */
    public void displayEnemySchoolboard(int playerOffset) {
        if (board != null) {
            if(playerOffset < board.getNumberOfPLayers()) {
                displayEnemyEntrance(playerOffset);
                displayEnemyTables(playerOffset);
                displayEnemyProfessors(playerOffset);
                displayEnemyAssistant(playerOffset);
                setTower(enemyTowerIcon, board.getGametable().getSchoolBoards()[(myPlayerId + playerOffset) % board.getNumberOfPLayers()].getTowersColor());
                Platform.runLater(() -> {
                    enemyTowerIcon.setFitHeight(20.0);
                    enemyTowerIcon.setFitWidth(30.0);
                });
            }
        }
    }

    /**
     * calls the displayEnemyAssistant method, passing as parameter enemyBoardDisplayed
     */
    public void displayEnemyAssistant() {
        displayEnemyAssistant(enemyBoardDisplayed);
    }

    /**
     * displays the assistants chosen by the other players
     * @param playerOffset
     */
    public void displayEnemyAssistant(int playerOffset) {
        if (board.getPlayedAssistants()[(myPlayerId + (playerOffset)) % board.getNumberOfPLayers()] != null) {
            enemyAssistant.setVisible(true);
            enemyAssistant.setImage(new Image("/images/Assistant/assistant" + (board.getPlayedAssistants()[(myPlayerId + (playerOffset)) % board.getNumberOfPLayers()].getAssistant().getCardValue() - 1) + ".png"));
        } else {
            enemyAssistant.setVisible(false);
        }
    }

    /**
     * calls the displayEnemyEntrance method, passing as parameter enemyBoardDisplayed
     */
    public void displayEnemyEntrance() {
        displayEnemyEntrance(enemyBoardDisplayed);
    }

    /**
     * displays the other players' entrance
     * @param playerOffset
     */
    public void displayEnemyEntrance(int playerOffset) {
        SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[(myPlayerId + (playerOffset)) % board.getNumberOfPLayers()];
        for (int i = 0; i < schoolBoard.getStudentsFromEntrance().length; i++) {
            setStudent(enemyEntrance.get(("enemyEntrance" + i)), schoolBoard.getStudentsFromEntrance()[i]);
        }
    }

    /**
     * calls the displayEnemyTables method, passing as parameter enemyBoardDisplayed
     */
    public void displayEnemyTables() {
        displayEnemyTables(enemyBoardDisplayed);
    }

    /**
     * displays the other players' tables placing on each one the students
     * @param playerOffset
     */
    public void displayEnemyTables(int playerOffset) {
        SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[(myPlayerId + (playerOffset)) % board.getNumberOfPLayers()];
        for (int i = 0; i < PawnColor.values().length; i++) {
            for (int j = 0; j < schoolBoard.getNumberOfStudentsOnTable(i); j++) {
                try {
                    setStudent(enemyTables.get(("enemyStudent" + i + "" + j)), new Student(PawnColor.associateIndexToPawnColor(i)));
                } catch (InvalidIndexException e) {
                    e.printStackTrace();
                }
            }
            for (int j = schoolBoard.getNumberOfStudentsOnTable(i); j < 10; j++) {
                setStudent(enemyTables.get(("enemyStudent" + i + "" + j)), null);
            }
        }
    }

    /**
     * calls the displayEnemyProfessors method, passing as a parameter enemyBoardDisplayed
     */
    public void displayEnemyProfessors() {
        displayEnemyProfessors(enemyBoardDisplayed);
    }

    /**
     * displays the other players' professors
     * @param playerOffset
     */
    public void displayEnemyProfessors(int playerOffset) {
        SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[(myPlayerId + (playerOffset)) % board.getNumberOfPLayers()];
        for (int i = 0; i < PawnColor.values().length; i++) {
            try {
                if (schoolBoard.getProfessors().contains(PawnColor.associateIndexToPawnColor(i))) {
                    setProfessor(enemyProfessors.get(("enemyProfessor" + i)), PawnColor.associateIndexToPawnColor(i));
                } else {
                    setProfessor(enemyProfessors.get(("enemyProfessor" + i)), null);
                    enemyProfessors.get(("enemyProfessor" + i)).setLayoutX(30.0 + (i * 42.0));
                    enemyProfessors.get(("enemyProfessor" + i)).setLayoutY(147.0);
                }
            } catch (InvalidIndexException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * calls the displayEnemyTowers method, passing as a parameter enemyBoardDisplayed
     */
    public void displayEnemyTowers() {
        displayEnemyTowers(enemyBoardDisplayed);
    }

    public void displayEnemyTowers(int playerOffset) {
        SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[(myPlayerId + (playerOffset)) % board.getNumberOfPLayers()];
        for (int i = 0; i < schoolBoard.getTowers().size(); i++)
            setTower(enemyTowers.get(("enemyTower" + i)), schoolBoard.getTowers().get(i).getColor());
        for (int i = schoolBoard.getTowers().size(); i < (board.getNumberOfPLayers() == 3 ? 6 : 8); i++)
            setTower(enemyTowers.get(("enemyTower" + i)), null);

    }

    /**
     * Displays the schoolboard of the actual player
     */
    public void displayMySchoolboard() {
        if (board != null) {
            SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[myPlayerId];
            displayMyEntrance();
            displayMyTables();
            displayMyProfessors();
            displayMyTowers();
            setTower(myTowerIcon, board.getGametable().getSchoolBoards()[myPlayerId].getTowersColor());
            Platform.runLater(() -> {
                myTowerIcon.setFitHeight(20.0);
                myTowerIcon.setFitWidth(30.0);
            });
            if (board.getGameMode() == GameMode.EXPERT) Platform.runLater(() -> coinNumber.setText(String.valueOf(board.getPlayesCoins(myPlayerId))));
        }
    }

    /**
     * Displays the assistant played by the actual player
     */
    public void displayMyAssistant() {
        if (board.getPlayedAssistants()[myPlayerId] != null) {
            myAssistant.setVisible(true);
            myAssistant.setImage(new Image("/images/Assistant/assistant" + (board.getPlayedAssistants()[myPlayerId].getAssistant().getCardValue() - 1) + ".png"));
        } else {
            myAssistant.setVisible(false);
        }
    }

    /**
     * Displays the entrance of the actual player
     */
    public void displayMyEntrance() {
        SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[myPlayerId];
        for (int i = 0; i < schoolBoard.getStudentsFromEntrance().length; i++) {
            setStudent(myEntranceImages.get(("myEntranceImage" + i)), schoolBoard.getStudentsFromEntrance()[i]);
        }
    }

    /**
     * Displays the tables of the actual player
     */
    public void displayMyTables() {
        //int dist = 22;
        SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[myPlayerId];
        for (int i = 0; i < PawnColor.values().length; i++) {
            for (int j = 0; j < schoolBoard.getNumberOfStudentsOnTable(i); j++) {
                try {
                    setStudent(myTablesImages.get(("studentImage" + i + "" + j)), new Student(PawnColor.associateIndexToPawnColor(i)));;
                } catch (InvalidIndexException e) {

                }
            }
            for (int j = schoolBoard.getNumberOfStudentsOnTable(i); j < 10; j++) {
                setStudent(myTablesImages.get(("studentImage" + i + "" + j)), null);
            }
        }
    }

    /**
     * Displays the professors of the actual player
     */
    public void displayMyProfessors() {
        SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[myPlayerId];
        for (int i = 0; i < PawnColor.values().length; i++) {
            try {
                if (schoolBoard.getProfessors().contains(PawnColor.associateIndexToPawnColor(i))) {
                    setProfessor(myProfessors.get(("myProfessor" + i)), PawnColor.associateIndexToPawnColor(i));
                }
                else {
                    setProfessor(myProfessors.get(("myProfessor" + i)), null);
                    myProfessors.get(("myProfessor" + i)).setLayoutX(30.0 + (i * 42.0));
                    myProfessors.get(("myProfessor" + i)).setLayoutY(147.0);
                }
            } catch (InvalidIndexException e) {
            }
        }
    }

    /**
     * Displays the towers of the actual player
     */
    public void displayMyTowers() {
        SchoolBoard schoolBoard = board.getGametable().getSchoolBoards()[myPlayerId];
        for (int i = 0; i < schoolBoard.getTowers().size(); i++) {
            setTower(myTowers.get(("myTower" + i)), schoolBoard.getTowers().get(i).getColor());
        }
        for (int i = schoolBoard.getTowers().size(); i < 8; i++) {
            setTower(myTowers.get(("myTower" + i)), null);
        }
    }

    /**
     * Displays the coins of the actual player
     */
    public void displayCoin() {
        Platform.runLater(() -> {
            coinImage.setVisible(true);
            coinNumber.setVisible(true);
            coinNumber.setText(String.valueOf(board.getPlayesCoins(myPlayerId)));
            coinTableImage.setVisible(true);
            coinTableImage.setImage(new Image("/images/coin.png"));
            coinTableNumber.setVisible(true);
            coinTableNumber.setText(String.valueOf(board.getTableCoins()));
        });
    }

    /**
     * returns the index of the Island passed as a parameter
     * @param island
     * @return
     */
    public int getIndexForIsland(Island island) {
        int index = -1;
        List<Integer> indexes = island.getIndex();
        OptionalInt minorIndex = indexes.stream().mapToInt(a -> a).min();
        if(minorIndex.isPresent()) {
            index = minorIndex.getAsInt();
        }
        return index;
    }

    /**
     * returns the Island correspondent to the index passed as a parameter
     * @param index
     * @return
     */
    public Island getIslandForIndex(int index) {
        Island island = null;
        List<Island> islands = board.getGametable() == null ? null : board.getGametable().getIslands();
        if(islands!= null) {
            for (int i = 0; i < islands.size(); i++) {
                if(islands.get(i).getIndex().contains(index)) {
                   island = islands.get(i);
                   break;
                }
            }
        }
        return island;
    }

    /**
     * Removes from visibleIslands the indexes of the islands that must not be displayed
     */
    public void adjustVisibleIslands() {
        List<Island> islands = board.getGametable().getIslands();
        if(islands!= null) {
            for (int i = 0; i < islands.size(); i++) {
                List<Integer> indexes = islands.get(i).getIndex();
                OptionalInt minorIndex = indexes.stream().mapToInt(a -> a).min();
                List<String> indexesAsStrings = new ArrayList<>();
                for(int j = 0; j < indexes.size(); j++)
                    indexesAsStrings.add(indexes.get(j).toString());
                if(minorIndex.isPresent()) {
                    indexesAsStrings.remove(String.valueOf(minorIndex.getAsInt()));
                    visibleIslands.removeAll(indexesAsStrings);
                }
            }
        }
    }

    /**
     * Displays the islands, rendering students, towers and mother nature
     */
    public void displayIslands() {
        Platform.runLater(() -> {
            GameTable gameTable = board.getGametable();
            List<Island> islands = gameTable == null ? null : board.getGametable().getIslands();

            if(islands != null) {
                for (int i = 0; i < 12; i++) {
                    if (islandPanes.get("islandPane" + i).getChildren().contains(motherNature)) {
                        islandPanes.get("islandPane" + i).getChildren().remove(motherNature);
                    }
                }

                adjustVisibleIslands();

                for (int i = 0; i < 12; i++)
                    if (!visibleIslands.contains(String.valueOf(i)))
                        islandPanes.get("islandPane" + i).setVisible(false);

                for (int i = 0; i < visibleIslands.size(); i++) {
                    if (islands.get(i).isForbidden()) {
                        prohibitionCard.get("prohibition" + getIndexForIsland(islands.get(i))).setImage(new Image("/images/Character/prohibition.png"));
                        prohibitionCard.get("prohibition" + getIndexForIsland(islands.get(i))).setVisible(true);
                    } else {
                        prohibitionCard.get("prohibition" + getIndexForIsland(islands.get(i))).setVisible(false);
                    }
                }
                islandPanes.get("islandPane" + getIndexForIsland(islands.get(gameTable.getMotherNaturePosition()))).getChildren().add(motherNature);
                motherNature.setX(50);
                motherNature.setY(40);

                for (int i = 0; i < islands.size(); i++) {
                    List<Student> students = islands.get(i).getStudents();
                    List<PawnColor> colors = new ArrayList<>();
                    for (Student student : students)
                        if (student != null)
                            colors.add(student.getColor());
                    int j = 0;
                    for (PawnColor color : PawnColor.values()) {
                        if (colors.contains(color)) {
                            setStudent(studentsOnIslands.get("student" + j + "OnIsland" + getIndexForIsland(islands.get(i))), new Student(color));
                            j++;
                        }
                    }
                }

                for (int i = 0; i < board.getGametable().getIslands().size(); i++) {
                    if(board.getGametable().getIslands().get(i).getTowers().size() > 0)
                        setTower(towersOnIslands.get("towerOnIsland" + getIndexForIsland(islands.get(i))), islands.get(i).getTowers().get(0).getColor());
                }
            }
        });
        //displayIslandEffect();
    }

    /*/** To be completed, we want to make it work before the presentation
     * renders all the islands and shows the ones in which mother nature could move to
     *
    public void displayIslandEffect() {
        Platform.runLater(() -> {
            if (board.getState() == 2 && board.getPlayerOnTurn() == myPlayerId) {
                for (int i = 0; i < board.getNumberOfPLayers(); i++) {
                    if (board.getPlayedAssistants()[i].getPlayerIndex() == myPlayerId) {
                        Assistant playedAssistant = board.getPlayedAssistants()[i].getAssistant();
                        int motherNatureMoves = (board.getGameMode() == GameMode.EXPERT && board.isPostmanActive()) ? playedAssistant.getMotherNatureMoves() + 2 : playedAssistant.getMotherNatureMoves();
                        if (visibleIslands.size() <= motherNatureMoves) {
                            for(String islandIndex : visibleIslands)
                                islands.get("island" + islandIndex).setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.GOLD, 30, 0.5, 0, 0));
                        } else {
                            int motherNatureRenderIsland = getIndexForIsland(board.getGametable().getIslands().get(board.getGametable().getMotherNaturePosition()));
                            while (motherNatureMoves > 0) {
                                int count = 0;
                                do {
                                    motherNatureRenderIsland = (motherNatureRenderIsland++) % 12;
                                    if (visibleIslands.contains(String.valueOf(motherNatureRenderIsland))) {
                                        islands.get("island" + motherNatureRenderIsland).setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.GOLD, 30, 0.5, 0, 0));
                                        break;
                                    }
                                    count++;
                                } while (count < 12);
                                motherNatureMoves--;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < visibleIslands.size(); i++) {
                    islands.get("island" + visibleIslands.get(i)).setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.CORNFLOWERBLUE, 30, 0.5, 0, 0));
                }
            }
        });
    }*/

    /**
     * displays the clouds and puts the students on them whether there are any
     */
    public void displayClouds() {
        if (board != null) {
            for(int i = 0; i < board.getNumberOfPLayers(); i++) {
                if (board.getClouds()[i].getStudents().size() != 0) {
                    for (int j = 0; j < board.getClouds()[i].getStudents().size(); j++) {
                        studentsClouds.get("cloud" + i + "Student" + j).setVisible(true);
                        setStudent(studentsClouds.get("cloud" + i + "Student" + j) , board.getClouds()[i].getStudents().get(j));
                    }
                } else {
                    for (int j = 0; j < 4; j++) {
                        studentsClouds.get("cloud" + i + "Student" + j).setVisible(false);
                    }
                }

            }
        }

    }

    /**
     * calls the displayBoard method passing as a parameter enemyBoardDisplayed
     */
    public void displayBoard() {
        displayBoard(enemyBoardDisplayed);
    }

    /**
     * displays all the elements of the board of the actual player and of the selected enemy player
     * @param playerOffset
     */
    public void displayBoard(int playerOffset) {
        showGameMessage();
        displayEnemySchoolboard(playerOffset);
        displayEnemyTowers(playerOffset);
        displayMySchoolboard();
        displayMyTowers();
        displayIslands();
        displayClouds();
        displayAssistants();
        if (board.getGameMode() == GameMode.EXPERT) {
            showButtonCenter();
            displayCharacter();
            displayCoin();
        }
    }

    /**
     * changes the enemy player board to be displayed
     * @param event
     */
    public void changeBoard(ActionEvent event) {
        switch(((Button) event.getSource()).getId()) {
            case "player2":
                displayBoard(1);
                break;
            case "player3":
                displayBoard(2);
                break;
            case "player4":
                displayBoard(3);
                break;
        }
    }

    /**
     * updates the scene based on the message passed by parameter
     * @param message
     */
    @Override
    public void update(Message message) {
        if (message instanceof TerminatorMessage) {
            terminate();
        } else if(message instanceof GameMessage && message != null) {
            board = (GameMessage) message;
            if (board.getState() == 100) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/endgameScene.fxml"));
                        try {
                            Parent root = loader.load();
                            EndgameController endgameController = loader.getController();
                            getClient().removeObserver(BoardController.this);
                            endgameController.setStage(getStage());
                            endgameController.setScene(new Scene(root));
                            endgameController.setRoot(root);
                            endgameController.setBoard(board);
                            getClient().addObserver(endgameController);
                            endgameController.setClient(getClient());
                            endgameController.getStage().setScene(endgameController.getScene());
                            endgameController.getStage().show();
                        } catch(IOException e) {
                            //Errore, spero non capiti
                        }
                    }
                });
            } else {
                board.renderWhatNeeded(this);
            }
        }
    }

    /**
     * when the assistant is clicked and it can be played, it grays out
     * @param event
     */
    @FXML
    public void assistantClick(MouseEvent event) {
        int indexCard = Integer.parseInt(((Button) event.getSource()).getId().substring(9));
        getClient().asyncWriteToSocket(new PlayAssistantMessage(myPlayerId, indexCard));
        if (studentMoved != -1) myEntrance.get("myEntrance" + studentMoved).setEffect(null);
    }

    /**
     * greys out the chosen student and sets the next state of the board
     * @param event
     */
    public void myEntranceClick(ActionEvent event) {
        int indexStudent = Integer.parseInt(((Button) event.getSource()).getId().substring(10));
        if (indexStudent == studentMoved) {
            myEntrance.get("myEntrance" + indexStudent).setEffect(null);
            studentMoved = -1;
            state = 0;
        } else {
            if (studentMoved != -1) myEntrance.get("myEntrance" + studentMoved).setEffect(null);
            myEntrance.get("myEntrance" + indexStudent).setEffect(new Glow(0.8));
            studentMoved = indexStudent;
            if (board.getGameMode() == GameMode.EXPERT) {
                if (board.getCharacters()[indexLastCharacterPlayed].getID() == 7 && board.getState() == 6) {
                    getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, indexStudent));
                } else if (board.getCharacters()[indexLastCharacterPlayed].getID() == 10 && board.getState() == 5) {
                    getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, indexStudent));
                }
            }
            state = 1;
        }
    }

    /**
     * puts the selected student on the right table or greys out the one chosen from the table
     * @param event
     */
    public void myTablesClick(ActionEvent event) {
        if (board.getGameMode() == GameMode.EXPERT && board.getCharacters()[indexLastCharacterPlayed].getID() == 10 && board.getState() == 6) {
            String button = ((Button) event.getSource()).getId();
            int indexTable = Integer.parseInt(button.substring(5,6));
            getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, indexTable));
        } else if (state == 1) {
            getClient().asyncWriteToSocket(new AddStudentOnTableMessage(myPlayerId, studentMoved));
            if (studentMoved != -1) myEntrance.get("myEntrance" + studentMoved).setEffect(null);
            state = 0;
        }
        if (studentMoved != -1) myEntrance.get("myEntrance" + studentMoved).setEffect(null);
    }

    /**
     * shows all the elements on the island
     * @param event
     */
    public void showStuffOnIslands(MouseEvent event) {
        Platform.runLater(() -> {
            int islandIndex = -1;
            if (((ImageView) event.getSource()).getId().substring(0, 1).equals("s"))
                islandIndex = Integer.valueOf(((ImageView) event.getSource()).getId().substring(16));
            else if (((ImageView) event.getSource()).getId().substring(0, 1).equals("i"))
                islandIndex = Integer.valueOf(((ImageView) event.getSource()).getId().substring(6));
            else if (((ImageView) event.getSource()).getId().substring(0, 1).equals("t"))
                islandIndex = Integer.valueOf(((ImageView) event.getSource()).getId().substring(13));
            if(islandIndex < 12 && islandIndex > -1) {
                Island island = getIslandForIndex(islandIndex);
                if (island != null) {
                    for (int i = 0; i < 5; i++) {
                        imageStudentsCounts.get("imageStudentsCount" + i).setVisible(true);
                        studentsCounts.get("studentsCount" + i).setVisible(true);
                        try {
                            studentsCounts.get("studentsCount" + i).setText("x" + island.getNumberOfStudentsForColor(PawnColor.associateIndexToPawnColor(i)));
                        } catch (InvalidIndexException e) {
                            // It is never thrown
                        }
                    }
                    if (island.getTowers().size() > 0) {
                        imageTowersCount.setVisible(true);
                        setTower(imageTowersCount, island.getTowers().size() == 0 ? null : island.getTowers().get(0).getColor());
                        imageTowersCount.setFitHeight(20);
                        towersCount.setVisible(true);
                        towersCount.setText("x" + String.valueOf(island.getTowers().size()));
                    }
                    assistantImage.setImage(new Image("images/Board/Islands/parchment.jpg", 80,130, false, false));
                    assistantImage.setVisible(true);
                    assistantImage.setTranslateY(-25.0);
                }
            }
        });
    }

    /**
     * hides all the elements on the islands
     * @param event
     */
    public void hideStuffOnIslands(MouseEvent event) {
        int islandIndex = -1;
        if (((ImageView) event.getSource()).getId().substring(0, 1).equals("s"))
            islandIndex = Integer.valueOf(((ImageView) event.getSource()).getId().substring(16));
        else if (((ImageView) event.getSource()).getId().substring(0, 1).equals("i"))
            islandIndex = Integer.valueOf(((ImageView) event.getSource()).getId().substring(6));
        else if (((ImageView) event.getSource()).getId().substring(0, 1).equals("t"))
            islandIndex = Integer.valueOf(((ImageView) event.getSource()).getId().substring(13));
        if(islandIndex < 12 && islandIndex > -1) {
            Island island = getIslandForIndex(islandIndex);
            if (island != null) {
                Platform.runLater(() -> {
                    for (int i = 0; i < 5; i++) {
                        imageStudentsCounts.get("imageStudentsCount" + i).setVisible(false);
                        studentsCounts.get("studentsCount" + i).setVisible(false);
                    }
                });
                Platform.runLater(() -> {
                    imageTowersCount.setVisible(false);
                    towersCount.setVisible(false);
                    assistantImage.setVisible(false);
                    assistantImage.setTranslateY(25.0);
                });
            }
        }
    }

    /**
     * send the correct message based on the current state of the clicked island
     * @param event
     */
    public void islandClick(MouseEvent event) {
        int indexClick = Integer.valueOf(((ImageView) event.getSource()).getId().substring(6));
        int islandIndex = Integer.valueOf(((ImageView) event.getSource()).getId().substring(6));
        List<Island> islands = board.getGametable() == null ? null : board.getGametable().getIslands();
        if(islands != null && indexClick < 12 && indexClick > -1) {
            if (state == 1) {
                getClient().asyncWriteToSocket(new AddStudentOnIslandMessage(myPlayerId, studentMoved, islandIndex));
                state = 0;
            } else if (state == 2) {
                    getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, islandIndex));
                    state = 0;
            } else if (board.getState() == 4 || board.getState() == 5) {
                getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, islandIndex));
            } else if (board.getState() == 2) {
                islandIndex = -1;
                for (int i = 0; i < islands.size(); i++) {
                    if (islands.get(i).getIndex().contains(indexClick)) {
                        islandIndex = i;
                        break;
                    }
                }
                getClient().asyncWriteToSocket(new ChangeMotherNaturePositionMessage(myPlayerId, islandIndex));
            } else if (board.getState() == 4 && board.getCharacters()[indexLastCharacterPlayed].getID() == 5) {
                getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, islandIndex));
            }
        }
        if (studentMoved != -1) myEntrance.get("myEntrance" + studentMoved).setEffect(null);
    }

    /**
     * sends a DoYourJobMessage when one of the three student charachters are played
     * @param event
     */
    public void playStudentCard(MouseEvent event) {
        String button = ((Button) event.getSource()).getId();
        int indexStudent = Integer.parseInt(button.substring(7,8));
        if (board.getCharacters()[indexLastCharacterPlayed].getID() == 1) {
            getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, indexStudent-1));
            state = 2;
        }
        if ((board.getCharacters()[indexLastCharacterPlayed].getID() == 7 || board.getCharacters()[indexLastCharacterPlayed].getID() == 7) && board.getState() == 5) {
            getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, indexStudent-1));
        }
        if (board.getCharacters()[indexLastCharacterPlayed].getID() == 11) {
            getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, indexStudent-1));
        }
    }

    /**
     * displays the assistants at the center of the screen
     * @param event
     */
    public void showAssistant(MouseEvent event) {
        int idAssistant = Integer.parseInt(((Button) event.getSource()).getId().substring(9));
        idAssistant--;
        String imagePath = "/images/Assistant/assistant" + idAssistant + ".png";
        Image imageAssistant = new Image(imagePath);
        Platform.runLater(() -> {
                assistantImage.setImage(imageAssistant);
                assistantImage.setVisible(true);
                assistantImage.setTranslateY(-8.0);
        });
    }

    /**
     * hides the previously showed assistant
     * @param event
     */
    public void removeShowAssistant(MouseEvent event) {
        Platform.runLater(() -> {
                assistantImage.setVisible(false);
                assistantImage.setTranslateY(8.0);
        });
    }

    /**
     * displays the assistant buttons at the bottom of the screen
     */
    public void displayAssistants() {
        List<Integer> assistantNotPlayed = new ArrayList<>();
        for (Assistant as : board.getAssistants()) assistantNotPlayed.add(as.getCardValue());
        for (int i = 1; i <= 10; i++) {
            String imagePath = "/images/Assistant/buttonAssistantB&W";
            Image imageBW;
            if (!assistantNotPlayed.contains(i)) {
                switch (i) {
                    case 1:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant1Image.setImage(imageBW);
                                assistant1.setMouseTransparent(true);

                        });
                        break;
                    case 2:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                            assistant2Image.setImage(imageBW);
                            assistant2.setMouseTransparent(true);
                        });
                        break;
                    case 3:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant3Image.setImage(imageBW);
                                assistant3.setMouseTransparent(true);
                        });
                        break;
                    case 4:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant4Image.setImage(imageBW);
                                assistant4.setMouseTransparent(true);
                        });
                        break;
                    case 5:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant5Image.setImage(imageBW);
                                assistant5.setMouseTransparent(true);
                        });
                        break;
                    case 6:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant6Image.setImage(imageBW);
                                assistant6.setMouseTransparent(true);
                        });
                        break;
                    case 7:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant7Image.setImage(imageBW);
                                assistant7.setMouseTransparent(true);
                        });
                        break;
                    case 8:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant8Image.setImage(imageBW);
                                assistant8.setMouseTransparent(true);
                        });
                        break;
                    case 9:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant9Image.setImage(imageBW);
                                assistant9.setMouseTransparent(true);
                        });
                        break;
                    case 10:
                        imagePath += i + ".png";
                        imageBW = new Image(imagePath);
                        Platform.runLater(() -> {
                                assistant10Image.setImage(imageBW);
                                assistant10.setMouseTransparent(true);
                        });
                        break;
                }
            }

            if (board.getPlayedAssistants()[myPlayerId] != null) {
                myAssistant.setVisible(true);
                myAssistant.setImage(new Image("/images/Assistant/assistant" + (board.getPlayedAssistants()[myPlayerId].getAssistant().getCardValue() - 1) + ".png"));
            } else {
                myAssistant.setVisible(false);
            }
        }
    }

    /**
     * sends a GetStudentsFromCloudMessage when a cloud is clicked
     * @param event
     */
    public void cloudClick(MouseEvent event) {
        int cloudIndex = Integer.parseInt(((ImageView) event.getSource()).getId().substring(((ImageView) event.getSource()).getId().length() - 1));
        getClient().asyncWriteToSocket(new GetStudentsFromCloudsMessage(myPlayerId, cloudIndex));
        if (studentMoved != -1) myEntrance.get("myEntrance" + studentMoved).setEffect(null);
    }

    /**
     * puts a students of the color passed by parameter on the node passed by parameter of the Character
     * @param node
     * @param student
     */
    public void setStudentCharacter(Button node, Student student) {
        Platform.runLater(() -> {
            if(node != null) {
                if (student != null) {
                    switch (student.getColor()) {
                        case GREEN:
                            ImageView imageView1 = new ImageView(new Image("/images/Board/Schoolboards/Students/green.png"));
                            imageView1.setFitHeight(30);
                            imageView1.setFitWidth(30);
                            node.setGraphic(imageView1);
                            break;
                        case RED:
                            ImageView imageView2 = new ImageView(new Image("/images/Board/Schoolboards/Students/red.png"));
                            imageView2.setFitHeight(30);
                            imageView2.setFitWidth(30);
                            node.setGraphic(imageView2);
                            break;
                        case YELLOW:
                            ImageView imageView3 = new ImageView(new Image("/images/Board/Schoolboards/Students/yellow.png"));
                            imageView3.setFitHeight(30);
                            imageView3.setFitWidth(30);
                            node.setGraphic(imageView3);
                            break;
                        case PINK:
                            ImageView imageView4 = new ImageView(new Image("/images/Board/Schoolboards/Students/pink.png"));
                            imageView4.setFitHeight(30);
                            imageView4.setFitWidth(30);
                            node.setGraphic(imageView4);
                            break;
                        case BLUE:
                            ImageView imageView5 = new ImageView(new Image("/images/Board/Schoolboards/Students/blue.png"));
                            imageView5.setFitHeight(30);
                            imageView5.setFitWidth(30);
                            node.setGraphic(imageView5);
                            break;
                    }
                } else {
                    ImageView imageView5 = new ImageView(new Image("/images/Board/Schoolboards/circle.png"));
                    imageView5.setFitHeight(30);
                    imageView5.setFitWidth(30);
                    node.setGraphic(imageView5);
                }
            }
        });
    }

    /**
     * displays the characters and their elements
     */
    public void displayCharacter() {
        if (board.getGameMode() == GameMode.EXPERT) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    character1Image.setVisible(true);
                    character2Image.setVisible(true);
                    character3Image.setVisible(true);

                    for (int i = 0; i < 3; i++) {
                        String path = "/images/Character/character" + board.getCharacters()[i].getID() + ".jpg";
                        Image image = new Image(path);
                        switch (i) {
                            case 0:
                                if (board.getCharacters()[i] instanceof Monk || board.getCharacters()[i] instanceof Princess) {
                                    for (int j = 0; j < 4; j++) {
                                        setStudentCharacter(studentsCards.get("student" + (j+1) + "Character" + (i+1)), ((CharacterWithStudent) board.getCharacters()[i]).getStudentsOnCard()[j]);
                                        studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                    }
                                } else if (board.getCharacters()[i] instanceof Jester) {
                                    for (int j = 0; j < 6; j++) {
                                        setStudentCharacter(studentsCards.get("student" + (j+1) + "Character" + (i+1)), ((CharacterWithStudent) board.getCharacters()[i]).getStudentsOnCard()[j]);
                                        studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                    }
                                } else if (board.getCharacters()[i] instanceof Healer) {
                                    for (int j = 0; j < 4; j++) {
                                        if (j >= ((Healer) board.getCharacters()[i]).getNumberOfProibitionCard()) {
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(false);
                                        } else {
                                            Image image2 = new Image("/images/Character/prohibition.png");
                                            ImageView imageView = new ImageView(image2);
                                            imageView.setFitHeight(30);
                                            imageView.setFitWidth(30);
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setGraphic(imageView);
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                        }

                                    }
                                }
                                character1Image.setImage(image);
                                if (board.getCharacters()[0].getFirstUse()) {
                                    coinCharacter1.setVisible(true);
                                    coinCharacter1.setMouseTransparent(true);
                                    coinCharacter1.setImage(new Image("/images/coin.png"));
                                }
                                break;
                            case 1:
                                if (board.getCharacters()[i] instanceof Monk || board.getCharacters()[i] instanceof Princess) {
                                    for (int j = 0; j < 4; j++) {
                                        setStudentCharacter(studentsCards.get("student" + (j+1) + "Character" + (i+1)), ((CharacterWithStudent) board.getCharacters()[i]).getStudentsOnCard()[j]);
                                        studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                    }
                                } else if (board.getCharacters()[i] instanceof Jester) {
                                    for (int j = 0; j < 6; j++) {
                                        setStudentCharacter(studentsCards.get("student" + (j+1) + "Character" + (i+1)), ((CharacterWithStudent) board.getCharacters()[i]).getStudentsOnCard()[j]);
                                        studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                    }
                                } else if (board.getCharacters()[i] instanceof Healer) {
                                    for (int j = 0; j < 4; j++) {
                                        if (j >= ((Healer) board.getCharacters()[i]).getNumberOfProibitionCard()) {
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(false);
                                        } else {
                                            Image image2 = new Image("/images/Character/prohibition.png");
                                            ImageView imageView = new ImageView(image2);
                                            imageView.setFitHeight(30);
                                            imageView.setFitWidth(30);
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setGraphic(imageView);
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                        }

                                    }
                                }
                                character2Image.setImage(image);
                                if (board.getCharacters()[1].getFirstUse()) {
                                    coinCharacter2.setVisible(true);
                                    coinCharacter2.setMouseTransparent(true);
                                    coinCharacter2.setImage(new Image("/images/coin.png"));
                                }
                                break;
                            case 2:
                                if (board.getCharacters()[i] instanceof Monk || board.getCharacters()[i] instanceof Princess) {
                                    for (int j = 0; j < 4; j++) {
                                        setStudentCharacter(studentsCards.get("student" + (j+1) + "Character" + (i+1)), ((CharacterWithStudent) board.getCharacters()[i]).getStudentsOnCard()[j]);
                                        studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                    }
                                } else if (board.getCharacters()[i] instanceof Jester) {
                                    for (int j = 0; j < 6; j++) {
                                        setStudentCharacter(studentsCards.get("student" + (j+1) + "Character" + (i+1)), ((CharacterWithStudent) board.getCharacters()[i]).getStudentsOnCard()[j]);
                                        studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                    }
                                } else if (board.getCharacters()[i] instanceof Healer) {
                                    for (int j = 0; j < 4; j++) {
                                        if (j >= ((Healer) board.getCharacters()[i]).getNumberOfProibitionCard()) {
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(false);
                                        } else {
                                            Image image2 = new Image("/images/Character/prohibition.png");
                                            ImageView imageView = new ImageView(image2);
                                            imageView.setFitHeight(30);
                                            imageView.setFitWidth(30);
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setGraphic(imageView);
                                            studentsCards.get("student" + (j+1) + "Character" + (i+1)).setVisible(true);
                                        }

                                    }
                                }
                                character3Image.setImage(image);
                                if (board.getCharacters()[2].getFirstUse()) {
                                    coinCharacter3.setVisible(true);
                                    coinCharacter3.setMouseTransparent(true);
                                    coinCharacter3.setImage(new Image("/images/coin.png"));
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    /**
     * sends a doYourJobMessage depending on which of the four students is selected in the last played character
     * @param event
     */
    public void sendButtonValue(MouseEvent event) {
        String button = ((Button) event.getSource()).getId();
        int index = -1;
        switch (button) {
            case "pawnColor0":
                index = 0;
                break;
            case "pawnColor1":
                index = 1;
                break;
            case "pawnColor2":
                index = 2;
                break;
            case "pawnColor3":
                index = 3;
                break;
            case "pawnColor4":
                index = 4;
                break;
        }
        if (board.getState() == 4) {
            if (board.getCharacters()[indexLastCharacterPlayed].getID() == 12 || board.getCharacters()[indexLastCharacterPlayed].getID() == 9) getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, index));
            else if (board.getCharacters()[indexLastCharacterPlayed].getID() == 7) getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, index+1));
            else if (board.getCharacters()[indexLastCharacterPlayed].getID() == 10) {
                if (index == 3) getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, 1));
                else getClient().asyncWriteToSocket(new DoYourJobMessage(myPlayerId, 2));
            }

            Platform.runLater( () -> {
                pawnColor0.setVisible(false);
                pawnColor1.setVisible(false);
                pawnColor2.setVisible(false);
                pawnColor3.setVisible(false);
                pawnColor4.setVisible(false);
            });
        }
    }


    /**
     * shows the selection of the students on character button at the center of the board
     */
    private void showButtonCenter() {
        if (board.getState() == 4 && board.getPlayerOnTurn() == myPlayerId) {
            if ((board.getCharacters()[indexLastCharacterPlayed].getID() == 12 || board.getCharacters()[indexLastCharacterPlayed].getID() == 9)) {
                Platform.runLater( () -> {
                    pawnColor0.setText("GREEN");
                    pawnColor1.setText("RED");
                    pawnColor2.setText("YELLOW");
                    pawnColor3.setText("PINK");
                    pawnColor4.setText("BLUE");
                    pawnColor0.setVisible(true);
                    pawnColor1.setVisible(true);
                    pawnColor2.setVisible(true);
                    pawnColor3.setVisible(true);
                    pawnColor4.setVisible(true);
                });
            } else if (board.getCharacters()[indexLastCharacterPlayed].getID() == 7) {
                Platform.runLater( () -> {
                    pawnColor0.setText("1");
                    pawnColor1.setText("2");
                    pawnColor2.setText("3");
                    pawnColor0.setVisible(true);
                    pawnColor1.setVisible(true);
                    pawnColor2.setVisible(true);
                });
            } else if (board.getCharacters()[indexLastCharacterPlayed].getID() == 10) {
                Platform.runLater( () -> {
                    pawnColor3.setText("1");
                    pawnColor4.setText("2");
                    pawnColor3.setVisible(true);
                    pawnColor4.setVisible(true);
                });
            }
        }

    }

    /**
     * returns the path of the image depending on the color passed by parameter
     * @param color
     * @return
     */
    private String returnImagePathStudentFromColor(PawnColor color) {
        switch (color) {
            case BLUE:
                return "/images/Board/Schoolboards/Students/blue.png";
            case GREEN:
                return "/images/Board/Schoolboards/Students/green.png";
            case PINK:
                return "/images/Board/Schoolboards/Students/pink.png";
            case RED:
                return "/images/Board/Schoolboards/Students/red.png";
            case YELLOW:
                return "/images/Board/Schoolboards/Students/yellow.png";
        }
        return "";
    }

    /**
     * shows the game message at the top of the board
     */
    public void showGameMessage(){
        Platform.runLater( () -> {
            String playerMessage = board.getPlayerMessageGui();
            labelGameMessage.setVisible(true);
            labelGameMessage.setText(playerMessage);
        });
    }

    /**
     * send an ActivateEffectMessage depending on which charachter has been selected
     * @param event
     */
    public void playCharacter(MouseEvent event) {
        int indexCard = -1;
        switch(((ImageView) event.getSource()).getId()) {
            case "character1Image":
                indexCard = 0;
                break;
            case "character2Image":
                indexCard = 1;
                break;
            case "character3Image":
                indexCard = 2;
                break;
        }
        getClient().asyncWriteToSocket(new ActivateEffectMessage(myPlayerId, indexCard));
        indexLastCharacterPlayed = indexCard;
    }


    /**
     * prints the effect of the selected character
     * @param indexCharacter
     * @return
     */
    private String returnEffectCharacter(int indexCharacter) {
        switch (indexCharacter) {
            case 1:
                return "Take 1 student from this card and place\nit on an Island of your choice";
            case 2:
                return "During this turn, you take control of any\nnumber of Professors even if you have\nthe same number of Students\nas the player who currently controls them";
            case 3:
                return "Choose an Island and resolve the Island\nas if Mother Nature had ended her movement\nthere.Mother Nature will still move and Island\nwhe she ends her movement will also be resolved";
            case 4:
                return "You may move Mother Nature up to\n2 additional Island than is indicated by\nthe Assistant card you've played";
            case 5:
                return "Place a No Entry tile on an Island\nof your choice. The first time Mother Nature\nends her movement there,put the No Entry tile\nback onto this card do not calculate\ninfluence on that Island";
            case 6:
                return "When resolving a Conquering on an Island,\nTower do not count towards influence";
            case 7:
                return "You may take uo 3 Students from this card\nand replace them with the number of\nStudents from you Entrance";
            case 8:
                return "During the influence calculation this turn,\nyou count as having 2 more influence";
            case 9:
                return "Choose a color of Student;\nduring the influence calculation this turn,\nthat color adds no influence";
            case 10:
                return "You may exchange up to 2 Students\nbetween your Entrance and your Dining Room";
            case 11:
                return "Take 1 Student from this card\nand place it in your Dining Room";
            case 12:
                return "Choose a type of Student; every player must\nreturn 3 Students of that type from their\nDining Room to the bag. If any player\nhas fewer than 3 Students of that type,\nreturn as many Students as they have";
        }
        return "";
    }

    /**
     * sets visible the characterEffect and its text
     * @param event
     */
    public void showCharacterEffect(MouseEvent event) {
        int indexCard = Integer.parseInt(((ImageView) event.getSource()).getId().substring(9,10));
        String text = returnEffectCharacter(board.getCharacters()[indexCard-1].getID());

        characterEffect.setVisible(true);
        characterEffect.setText(text);
    }

    /**
     * hides the charachterEffect
     * @param event
     */
    public void removeCharacterEffect(MouseEvent event) {
        characterEffect.setVisible(false);
    }
}
