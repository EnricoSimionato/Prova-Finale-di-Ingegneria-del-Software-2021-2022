package it.polimi.ingsw.model;

public class AssistantStorage {

    private static final String assistants = "1,1;2,1;3,2;4,2;5,3;6,3;7,4;8,4;9,5;10,5";

    /**
     * Returns the value stored, which represents the values and the mother nature moves of all the possible assistants. The format is: "value 1,mother nature moves 1; value 2, ..."
     * @return values and the mother nature moves of all the possible assistants, returned as a sigle string
     */
    public static String getAssistants() { return assistants;}

}
