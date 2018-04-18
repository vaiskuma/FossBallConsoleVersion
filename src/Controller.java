/**
 * Created by Kompas on 2017-04-06.1
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Controller {

    //All of these are assigned for combo boxes
    @FXML
    private ComboBox<String> PlayerListInUpdateAndRead;
    @FXML
    private ComboBox<String> TeamListInUpdateAndRead;
    @FXML
    private ComboBox<String> chooseForTeamPlayerONE ;//when create teams
    @FXML
    private ComboBox<String> chooseForTeamPlayerTWO ;
    @FXML
    private ComboBox<String> chooseToUpdateTeamPlayesONE;//when updating teams
    @FXML
    private ComboBox<String> chooseToUpdateTeamPlayesTWO;
    @FXML
    private ComboBox<String> pickTournamentDate;

    //Tournament comboBoxes for all the teams and rounds
    @FXML
    private ComboBox<String> Team1R1;//Round 1
    @FXML
    private ComboBox<String> Team2R1;
    @FXML
    private ComboBox<String> Team3R1;
    @FXML
    private ComboBox<String> Team4R1;
    @FXML
    private ComboBox<String> Team1R2;//Round 2
    @FXML
    private ComboBox<String> Team2R2;
    @FXML
    private ComboBox<String> winnerTeam;//WINNER

    @FXML
    private TextField createTeamName;
    @FXML
    private TextField nameInput, birthInput, emailInput, idInput, idTeamInput, idTournamentInput ;//create for players
    @FXML
    private TextField readName; //read for players
    @FXML
    private TextField readbirth;//read for players
    @FXML
    private TextField readEmail;//read for players
    @FXML
    private TextField readRank; //read for players
    @FXML
    private TextField tournamentDateInput; //create the date for tournament

    @FXML
    private TextField readPlayerONE;//read for teams
    @FXML
    private TextField readPlayerTWO;//read for teams
    @FXML
    private TextField readTeamName; //read for teams

    @FXML
    private TextField inputScore1;//send tournament score to database
    @FXML
    private TextField inputScore2;//send tournament score to database
    @FXML
    private TextField inputScore3;//send tournament score to database

    //SAVE tournament current information about teams and score // Tournament tab -------------------------------------------------
    @FXML
    private void saveActionForCurrentTournament(ActionEvent event) {

        String Team1InRound1 = Team1R1.getValue();//we are getting the current value from the comboboxes and storing into the string value
        String Team2InRound1 = Team2R1.getValue();
        String Team3InRound1 = Team3R1.getValue();
        String Team4InRound1 = Team4R1.getValue();

        String Team1InRound2 = Team1R2.getValue();
        String Team2InRound2 = Team2R2.getValue();

        String TeamThatWon = winnerTeam.getValue();

        String readScore1 = inputScore1.getText();
        String readScore2 = inputScore2.getText();
        String readScore3 = inputScore3.getText();

        String tourDate = tournamentDateInput.getText();

        String readID = idTournamentInput.getText();

        try {

            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            //sql commands to update the current values stored in the data base
            String sql3 =
                    "UPDATE  Tournaments    " +
                    "SET Team1R1          = " + "'" + Team1InRound1  + "'"
                    + ", Team2R1          = " + "'" + Team2InRound1  + "'"
                    + ", Team3R1          = " + "'" + Team3InRound1  + "'"
                    + ", Team4R1          = " + "'" + Team4InRound1  + "'"
                    + ", Team1R2          = " + "'" + Team1InRound2  + "'"
                    + ", Team2R2          = " + "'" + Team2InRound2  + "'"
                    + ", WinnerTeam       = " + "'" + TeamThatWon    + "'"
                    + ", Score1           = " + "'" + readScore1     + "'"
                    + ", Score2           = " + "'" + readScore2     + "'"
                    + ", Score3           = " + "'" + readScore3     + "'"
                    + ", TournamentDate   = " + "'" + tourDate       + "'"
                    + "WHERE Tournaments. TournamentID ="+ readID;

            System.out.println(sql3);
            stmt.executeUpdate(sql3);
            con.close();//resultSet.close() statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //GIVE action it gives score to the players that won //Tournament tab--------------------------------------------------------------------------------
    @FXML
    private void giveScoreToThePlayersThatWon(ActionEvent event) {

        List<String> playersThatOne = new ArrayList<String>();
        String winners = winnerTeam.getValue();
        //The if makes sure that there is no crash if there is no value in the combo box
        if (winners != "null" || winners != " ") {
            System.out.println("<<SORRY BUT SOMETHING WENT TITS UP>>");

            try {

                String sql =
                        "UPDATE Teams " +
                        "SET Score = Score+ 1 " +
                        "WHERE Teams.TeamName = '" + winners + "'";

                Connection con = DBConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                            "SELECT Member1, Member2 " +
                                "FROM Teams " +
                                "WHERE TeamName =" + "'" + winners + "'");

                while (rs.next()) {//rs.Setnext()
                    playersThatOne.add(
                            rs.getString(1));//This is were you get the info from data base (name, id, age...)
                    playersThatOne.add(
                            rs.getString(2));
                    playersThatOne.add(
                            rs.getString(2));
                }
                String playerWhoManagedToWin1 = playersThatOne.get(0);// winner 1
                String playerWhoManagedToWin2 = playersThatOne.get(1); //winner 2

                String sql1 =
                        "UPDATE `Players` " +
                        "SET    `PlayerScore` = `PlayerScore` + 1 " +
                        "WHERE  `Players`.`name` = '" + playerWhoManagedToWin1 + "'";

                String sql2 =
                        "UPDATE `Players` " +
                        "SET    `PlayerScore` = `PlayerScore` + 1 " +
                        "WHERE  `Players`.`name` = '" + playerWhoManagedToWin2 + "'";

                System.out.println(sql);
                System.out.println(sql1);
                System.out.println(sql2);

                stmt.executeUpdate(sql);
                stmt.executeUpdate(sql1);
                stmt.executeUpdate(sql2);

                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

        System.out.println("<<THERE HAS TO BE VALUE IN THE WINNER COMBOBOX>>");
        }
        }

    //LOAD action for Team players //Create tab--------------------------------------------------------------------------------
    @FXML
    public void loadActionTeamPlayers(MouseEvent mouseEvent) {// this method is USED FOR CREATING A TEAM OUT OF 2 PLAYERS

        List<String> members = new ArrayList<String>();

        try {
            Connection con = DBConnection.getConnection(); //load players to comboBox
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Players");

            while (rs.next()) {
                members.add(
                    rs.getString(2));
            }
            con.close();

            ObservableList<String> list = FXCollections.observableArrayList();

            String listString = "";

            for (String s : members) {
                listString += list.add(s);
            }
            chooseForTeamPlayerONE.setItems(list);
            chooseForTeamPlayerTWO.setItems(list);
            chooseToUpdateTeamPlayesONE.setItems(list);
            chooseToUpdateTeamPlayesTWO.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //LOAD action for Teams //Update&Read tab--------------------------------------------------------------------------------
    @FXML
    public void loadActionUpdateForTeams(MouseEvent mouseEvent) {

        List<String> members = new ArrayList<String>();

        try {
            Connection con = DBConnection.getConnection(); //load players to comboBox

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Teams");
            while (rs.next()) {
                members.add(
                        rs.getInt(1) +" "
                                +   rs.getString(4));
            }
            con.close();

            ObservableList<String> list = FXCollections.observableArrayList();

            String listString = "";

            for (String s : members) {
                listString += list.add(s);
            }
            TeamListInUpdateAndRead.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Load action for Players //Update&Read tab--------------------------------------------------------------------------------
    @FXML
    public void loadActionUpdateForPlayers(MouseEvent mouseEvent) {

        List<String> members = new ArrayList<String>();

        try {
            Connection con = DBConnection.getConnection(); //load players to comboBox

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * " +
                        "FROM Players");
            while (rs.next()) {
                members.add(
                        rs.getInt(1) +" "
                              +   rs.getString(2));
            }
           con.close();

            ObservableList<String> list = FXCollections.observableArrayList();

            String listString = "";

            for (String s : members) {
                listString += list.add(s);
            }
            PlayerListInUpdateAndRead.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //READ action for Team information //Update&Read tab--------------------------------------------------------------------------------
    @FXML
    public void readActionForTeams(ActionEvent event){

        String readID = idTeamInput.getText();

        try {

            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                        "SELECT * " +
                            "FROM Teams " +
                            "WHERE TeamID = " + readID);
            while (rs.next()) {

                readPlayerONE.setText(rs.getString(2));
                readPlayerTWO.setText(rs.getString(3));
                readTeamName.setText(rs.getString(4));
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //READ action for Player information //Update&Read tab--------------------------------------------------------------------------------
    @FXML
    public void readActionForPlayers(ActionEvent event){

        String readID = idInput.getText();

        try {
            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                        "SELECT * " +
                            "FROM Players " +
                            "WHERE ID = " + readID);
            while (rs.next()) {

                readName.setText(rs.getString(2));
                readbirth.setText(rs.getString(3));
                readEmail.setText(rs.getString(4));
                readRank.setText(rs.getString(5));
            }
           con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //DELETE action for Players //Update&Read tab--------------------------------------------------------------------------------
    @FXML
    public void deleteActionForPlayers(ActionEvent actionEvent){

        String deleteID = idInput.getText();

        try {
            String sql = "DELETE FROM `Players` " +
                         "WHERE `ID` = " + deleteID+ ";";

            System.out.println(sql);

            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //DELETE action for Teams //Update&Read tab--------------------------------------------------------------------------------
    @FXML
    public void deleteActionForTeams(ActionEvent actionEvent){

        String deleteID = idTeamInput.getText();

        try {
            String sql = "DELETE FROM `Teams` " +
                         "WHERE `TeamID` = " + deleteID+ ";";

            System.out.println(sql);

            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
           con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //UPDATE action for players //Update&Read tab--------------------------------------------------------------------------------
    @FXML
    public void updateActionForPlayers(ActionEvent actionEvent){
      String id = idInput.getText();
      String name =  readName.getText();
      String birth = readbirth.getText();
      String email = readEmail.getText();

        try {
            String sql =  "UPDATE Players " +
                          "SET name =     "+ "'" + name  + "'" +
                            ", birth = + " + "'" + birth + "'" +
                            ", email ="    + "'"  + email + "'" +
                          "WHERE Players. ID =" + id;

            System.out.println(sql);

            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //UPDATE action for Teams //Update&Read tab--------------------------------------------------------------------------------
    @FXML
    public void updateActionForTeams(ActionEvent actionEvent){
        String idTeam = idTeamInput.getText();
        String player1 = chooseToUpdateTeamPlayesONE.getValue();//readPlayerONE.getText();
        String player2 = chooseToUpdateTeamPlayesTWO.getValue();//readPlayerTWO.getText();
        String teamName = readTeamName.getText();

        try {
            String sql =  "UPDATE Teams  "  +
                          "SET Member1 = " + "'" + player1 + "'" +
                          ", Member2 =  +" + "'" + player2 + "'" +
                          ", TeamName ="   + "'" + teamName+ "'" +
                          "WHERE Teams. TeamID =" + idTeam;

            System.out.println(sql);

            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
           con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //CREATE action for Team //Create tab--------------------------------------------------------------------------------
    @FXML
    public void createActionForTeams(ActionEvent actionEvent){

        String teamName = createTeamName.getText() ;
        String player1Name =chooseForTeamPlayerONE.getValue(); //member1
        String player2Name =chooseForTeamPlayerTWO.getValue(); //member2

        System.out.println("Name ->" + teamName + "<-");


        try {
            String sql = "INSERT INTO Teams " +
                         "VALUES " +
                         "(NULL, " +"'"+ player1Name +"'"+ ", " + "'"     + player2Name+  "' "  +  " ," +   "'"     +teamName+  "'     "+"," +" "+ 0 + ");";

            System.out.println(sql);

            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //CREATE action for Players //Create tab--------------------------------------------------------------------------------
    @FXML
    public void createActionForPlayers(ActionEvent actionEvent) {

        String name = nameInput.getText();
        String birth = birthInput.getText();
        String email = emailInput.getText();

        System.out.println("Name ->" + name + "<-");

        try {
            String sql = "INSERT INTO Players " +
                         "VALUES " +
                         "(NULL, " +"'"+ name +"'"+ ", " + birth+"," +   "'"     +email+  "'     "+"," +" "+ 0 + ");";

            System.out.println(sql);

            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //LOAD action for Teams //Tournament tab--------------------------------------------------------------------------------
    @FXML
    public void loadActionForTeamsTournaments(ActionEvent actionEvent ){

        List<String> members = new ArrayList<String>();

        try {
            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * " +
                                                 "FROM Teams");

            while (rs.next()) {
                members.add(
                        rs.getString(4));
            }
           con.close();

            ObservableList<String> list = FXCollections.observableArrayList();

            String listString = "";

            for (String s : members) {
                listString += list.add(s);
            }
            Team1R1.setItems(list);
            Team2R1.setItems(list);
            Team3R1.setItems(list);
            Team4R1.setItems(list);

            Team1R2.setItems(list);
            Team2R2.setItems(list);

            winnerTeam.setItems(list);
           // pickTournamentDate.setItems(list);

            //inputScore3.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    //LOAD action for tournament date //Tournament tab--------------------------------------------------------------------------------
    @FXML
    public void loadActionForDates(MouseEvent mouseEvent){
        List<String> members = new ArrayList<String>();

        try {
            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * " +
                                                 "FROM Tournaments");

            while (rs.next()) {
                members.add(
                        rs.getString(12));
            }
            con.close();

            ObservableList<String> list = FXCollections.observableArrayList();

            String listString = "";

            for (String s : members) {
                listString += list.add(s);
            }
            pickTournamentDate.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //READ action for ALL the tournament teams //Tournament tab--------------------------------------------------------------------------------
    @FXML
    public void readActionForALLTheTournamentInformation(ActionEvent actionEvent){
        String readID = idTournamentInput.getText();

        try {
            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * " +
                                                 "FROM Tournaments " +
                                                 "WHERE TournamentID = " + readID);

            while (rs.next()) {

                Team1R1.setValue(rs.getString(2));
                Team2R1.setValue(rs.getString(3));
                Team3R1.setValue(rs.getString(4));
                Team4R1.setValue(rs.getString(5));

                Team1R2.setValue(rs.getString(6));
                Team2R2.setValue(rs.getString(7));

                winnerTeam.setValue(rs.getString(8));
                pickTournamentDate.setValue(rs.getString(12));

                inputScore1.setText(rs.getString(9));
                inputScore2.setText(rs.getString(10));
                inputScore3.setText(rs.getString(11));
            }
           con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //RESET action reset the tournament information to 0's and blanks // Tournament tab--------------------------------------------------------------------------------
    @FXML
    public  void resetActionForTournament(ActionEvent actionEvent){
        //This is where we are setting all the values to noting because we are sorta preparing for a new tournament
        Team1R1.setValue(" ");
        Team2R1.setValue(" ");
        Team3R1.setValue(" ");
        Team4R1.setValue(" ");

        Team1R2.setValue(" ");
        Team2R2.setValue(" ");

        winnerTeam.setValue(" ");
        pickTournamentDate.setValue("Tournament Date");

        inputScore1.setPromptText(" score");
        inputScore2.setPromptText(" score");
        inputScore3.setPromptText(" score");

        tournamentDateInput.setPromptText(" YYYY MM DD");
    }
    @FXML
    public  void shutDown(){
        System.exit(0);
    }
}
