package Pang_Dean_Project;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * PassManager stores Passwords inside and keeps them safe
 *
 * @author Timothy and Zachary
 */
public class Pang_Dean_Project extends Application{

    /**
    * @param btnSetPin Used for setting the passcode at the login screen when app is first used.
    * @param btnResetPin Used for resetting the passcode at login screen
    * @param btnEnterReset Used for putting in previous passcode when reseting at login screen
    * @param btnConfirmReset Used for confirming new passcode at login screen
    * @param btnCancelReset used for canceling reset of passcode at login screen
    * @param titleBox Used for stacking app title and dynamic pin input at login screen.
    * @param loginStack Used for layering btnSetPin and btnLogin so btnSetPin can be made invisible, appearing as though it changed to btnLogin
    * @param cancelResetStack Used to stack btnReset and btnCancelReset at login screen
    * @param code used to dynamically display input on login screen
    * @param enterPin used with code to dynamically display input at login screen.  Compared to correctPin to allow user access to app
    * @param correctPin reads from pin.txt and compares to enterPin to allow user access to app
    * @param btnKey used for storing buttons to be used for passcode on login screen
    */
    
    Scene scene1, scene2, scene3, scene4, splashScene;
    Button btnLogin, btnReset, btnAdd, btnLogout, btnSave,  btnCancel, btnSetPin, btnResetPin, btnEnterReset, btnConfirmReset, btnCancelReset;
    Label lblTitle, lblInstruct, lblReg, lblPassSave;
    TextField txtcode, txtLink, txtUserName, txtPassword, txtWebsite, txtUser, txtPass;
    BorderPane login, passcodes, adding, splashPane;
    GridPane keyPad,inputPane, display;
    ListView<Password> list;
    VBox codescreen, layout, website, create, logReset, titleBox, splashBox;
    HBox link;
    StackPane title, loginStack, cancelResetStack, splashStack;
    Text code;
    String enterPin = "";
    ArrayList<Button> btnKey;
    ArrayList<Password> passwords;
    FadeTransition splashScreenIn, splashScreenOut, splashTitleIn, splashTitleOut, splashTitleInvisible;
    Image logo, logoTitle;
    ImageView logoView, logoTitleView;
    Login pin;
    JSONArray passwordsJSON;
    File passwordSave;
    
    /**
     * @param args the command line arguments
     */    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//--------------------------BUTTONS --------------------------------------------

        DropShadow shadow = new DropShadow();
        pin = new Login();
        passwordsJSON = new JSONArray();
        
        btnSetPin = new Button("Confirm");
        btnSetPin.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnSetPin.setStyle("-fx-background-color: #46A7AD;");
        btnSetPin.setFont(Font.font("Cambria", 16));
        btnSetPin.setEffect(shadow);
        btnSetPin.setOnAction(e -> {
                if(enterPin.length() < 4)
                {
                    resetPin("Passcode must be atleast 4 digits long");
                }
                else
                {
                    pin.setPin(enterPin);
                    resetPin("Passcode Set!");
                    btnSetPin.setVisible(false);
                    btnLogin.setVisible(true);
                    btnResetPin.setVisible(true);
                    btnCancelReset.setVisible(false);
                }
        });

        btnLogin = new Button("Login");
        btnLogin.setVisible(false);
        btnLogin.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnLogin.setStyle("-fx-background-color: #46A7AD;");
        btnLogin.setFont(Font.font("Cambria", 16));
        btnLogin.setEffect(shadow);
        btnLogin.setOnAction(e -> {
                if(enterPin.equals(pin.getCode()))
                {
                    primaryStage.setScene(scene2);
                    resetPin();
                }
                else
                {
                    resetPin("Wrong Code");
                }
        });
        
        btnReset = new Button("Reset");
        btnReset.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnReset.setStyle("-fx-background-color: #46A7AD;");
        btnReset.setFont(Font.font("Cambria", 16));
        btnReset.setEffect(shadow);
        btnReset.setOnAction(e -> {
                resetPin();
        });
        
        btnEnterReset = new Button("Enter");
        btnEnterReset.setVisible(false);
        btnEnterReset.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnEnterReset.setStyle("-fx-background-color: #46A7AD;");
        btnEnterReset.setFont(Font.font("Cambria", 16));
        btnEnterReset.setEffect(shadow);
        btnEnterReset.setOnAction(e -> {
                if(enterPin.equals(pin.getCode())){
                    btnEnterReset.setVisible(false);
                    btnSetPin.setVisible(true);
                    resetPin("Please Enter New Passcode");
                }
                else
                {
                    resetPin("Incorrect, Please Try Again");
                }
                    
        });
        
        btnCancelReset = new Button("Cancel Passcode Reset");
        btnCancelReset.setVisible(false);
        btnCancelReset.setAlignment(Pos.CENTER);
        btnCancelReset.setStyle("-fx-background-color: #A1ACB8;");
        btnCancelReset.setFont(Font.font("Cambria", 15));
        btnCancelReset.setOnAction(e -> {
                resetPin("Reset Cancelled");
                btnLogin.setVisible(true);
                btnResetPin.setVisible(true);
                btnEnterReset.setVisible(false);
                btnCancelReset.setVisible(false);
                btnSetPin.setVisible(false);
        });
        
        btnLogout = new Button("Logout");
        btnLogout.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnLogout.setStyle("-fx-background-color: #46A7AD;");
        btnLogout.setFont(Font.font("Cambria", 16));
        btnLogout.setEffect(shadow);
        btnLogout.setOnAction(e -> primaryStage.setScene(scene1));
        
        btnAdd = new Button("Add New Password");
        btnAdd.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnAdd.setStyle("-fx-background-color: #46A7AD;");
        btnAdd.setFont(Font.font("Cambria", 16));
        btnAdd.setEffect(shadow);
        btnAdd.setOnAction(e -> primaryStage.setScene(scene3));
        
        btnSave = new Button("Save");
        btnSave.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnSave.setStyle("-fx-background-color: #46A7AD;");
        btnSave.setFont(Font.font("Cambria", 16));
        btnSave.setEffect(shadow);
        btnSave.setOnAction(e -> {
            
            if(txtLink.getText().length() == 0 || txtUserName.getText().length() == 0 || txtPassword.getText().length() == 0)
            {
                lblPassSave.setText("Please fill all fields");
            }
            else{
            primaryStage.setScene(scene2);
            addPassword();
            savePasswords();
            lblPassSave.setText("");
                }
        });
        
        
        btnCancel = new Button("Cancel");
        btnCancel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnCancel.setStyle("-fx-background-color: #46A7AD;");
        btnCancel.setFont(Font.font("Cambria", 16));
        btnCancel.setOnAction(e -> primaryStage.setScene(scene2));
        
        btnResetPin = new Button("Click here to create a passcode");
        btnResetPin.setVisible(false);
        btnResetPin.setAlignment(Pos.CENTER);
        btnResetPin.setStyle("-fx-background-color: #A1ACB8;");
        btnResetPin.setFont(Font.font("Cambria", 15));
        btnResetPin.setOnAction(e -> {
            resetPin("Please enter current passcode to change");
            btnEnterReset.setVisible(true);
            btnLogin.setVisible(false);
            btnCancelReset.setVisible(true);
            btnResetPin.setVisible(false);
        });
                
//--------------------------SCANNER STUFF----------------------------------------
        if(pin.getCode().length() != 0)
        {
            btnSetPin.setVisible(false);
            btnLogin.setVisible(true);
            btnResetPin.setVisible(true);
            code = new Text("Please enter your code to access the passwords");
        }
        else {code = new Text("Please Enter a Passcode");}
        
//--------------------------TEXTFIELDS ------------------------------------------

        txtLink = new TextField();
        txtLink.setPrefColumnCount(20);
        
        txtWebsite = new TextField();
        txtWebsite.setPrefColumnCount(10);
        
        txtUserName = new TextField();
        txtUserName.setPrefColumnCount(20);
        
        txtUser = new TextField();
        txtUser.setPrefColumnCount(10);
        
        txtPassword = new TextField();
        txtPassword.setPrefColumnCount(20);
        
        txtPass = new TextField();
        txtPass.setPrefColumnCount(10);
        
        txtcode = new TextField();
        txtcode.setMaxSize(Double.MAX_VALUE, Double.MIN_VALUE);
        
//--------------------------LABELS ----------------------------------------------

        lblTitle = new Label("PassManager");
        lblTitle.setFont(Font.font("Cambria", 40));
        
        lblPassSave = new Label("");
        lblPassSave.setFont(Font.font("Cambria", 15));
        
        
//----------------Captian Zachary's Whacky Unnessesary SplashScreen!-------------
        logo = new Image("file:logo.png");
        logoView = new ImageView(logo);
        splashScreenIn = new FadeTransition(Duration.millis(2000), logoView);
        splashScreenIn.setFromValue(0);
        splashScreenIn.setToValue(1);
        splashScreenIn.setCycleCount(1);
        
        splashScreenOut = new FadeTransition(Duration.millis(2000), logoView);
        splashScreenOut.setFromValue(1);
        splashScreenOut.setToValue(0);
        splashScreenOut.setCycleCount(1);
        
        logoTitle = new Image("file:logo2.png");
        logoTitleView = new ImageView(logoTitle);
        splashStack = new StackPane();
        splashStack.getChildren().addAll(logoTitleView,logoView);
        splashTitleIn = new FadeTransition(Duration.millis(2000), logoTitleView);
        splashTitleIn.setFromValue(0);
        splashTitleIn.setToValue(1);
        splashTitleIn.setCycleCount(1);
        
        splashTitleInvisible = new FadeTransition(Duration.millis(2000), logoTitleView);
        splashTitleInvisible.setFromValue(0);
        splashTitleInvisible.setToValue(0);
        splashTitleInvisible.setCycleCount(1);
        
        splashTitleOut = new FadeTransition(Duration.millis(2000), logoTitleView);
        splashTitleOut.setFromValue(1);
        splashTitleOut.setToValue(0);
        splashTitleOut.setCycleCount(1);

        //splashScreenIn.play();

        splashScreenIn.setOnFinished((e) -> {
            splashTitleIn.play();
            //logoTitleView.setVisible(true);
        });

        splashScreenOut.setOnFinished((e) -> {
            primaryStage.setScene(scene1);
        });
        
        splashTitleIn.setOnFinished((e) -> {
            splashScreenOut.play();
            splashTitleOut.play();
        });
//--------------------------LISTVIEW -------------------------------------------

        list = new ListView<>();
        list.setPrefWidth(160);
        
//-------------------------PASSWORDS--------------------------------------------
        passwords = new ArrayList<>();
        passwordSave = new File("passwords.json");
        passwordsJSON = new JSONArray();
        
        readPasswords();
//--------------------------PANES -----------------------------------------------
        splashPane = new BorderPane();
        splashPane.setStyle("-fx-background-color: #A1ACB8");
        login = new BorderPane();
        login.setStyle("-fx-background-color: #A1ACB8");
        passcodes = new BorderPane();
        passcodes.setStyle("-fx-background-color: #A1ACB8");
        adding = new BorderPane();
        adding.setStyle("-fx-background-color: #A1ACB8");
        
        setSplash(splashPane);
        
        setT(login);
        setC(login);
        setB(login);

        setTop(passcodes);
        setCenter(passcodes);
        setBottom(passcodes);
        
        sett(adding);
        setc(adding);
        setb(adding);

//-------------------------ADDING EVERYTHING TO SCENES -------------------------
        splashScene = new Scene(splashPane, 390, 490);
        scene1 = new Scene(login, 400, 500);
        scene2 = new Scene(passcodes, 400, 500);
        scene3 = new Scene(adding, 400, 500);

        primaryStage.setResizable(false);
        primaryStage.setTitle("PassManager");
        primaryStage.setScene(splashScene);
        primaryStage.show();

    }

//-------------------------- LOGIN SCREEN --------------------------------------    
    private void setT(BorderPane login) {
        titleBox = new VBox(20);
        titleBox.getChildren().addAll(lblTitle, code);
        titleBox.setAlignment(Pos.CENTER);
        login.setTop(titleBox);
        
    }

    private void setC(BorderPane login) {
        
        String[] keys
                = {
                    "1", "2", "3",
                    "4", "5", "6",
                    "7", "8", "9",
                    "", "0"
                };

        keyPad = new GridPane();
        
        btnKey = new ArrayList<>();
        
        for (int i = 0; i < 11; i++) {
            btnKey.add(new Button(keys[i]));
            btnKey.get(i).setAlignment(Pos.CENTER);
            btnKey.get(i).setStyle("-fx-background-color: #A1ACB8;");
            btnKey.get(i).setFont(Font.font("Cambria", 35));
            keyPad.add(btnKey.get(i), i % 3 , (int) Math.ceil(i / 3));
            
        }
        
        btnKey.get(0).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "1";}
                code.setText(enterPin);
        });
        btnKey.get(1).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "2";}
                code.setText(enterPin);
        });
        btnKey.get(2).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "3";}
                code.setText(enterPin);
        });
        btnKey.get(3).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "4";}
                code.setText(enterPin);
        });
        btnKey.get(4).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "5";}
                code.setText(enterPin);
        });
        btnKey.get(5).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "6";}
                code.setText(enterPin);
        });
        btnKey.get(6).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "7";}
                code.setText(enterPin);
        });
        btnKey.get(7).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "8";}
                code.setText(enterPin);
        });
        btnKey.get(8).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "9";}
                code.setText(enterPin);
        });
        btnKey.get(10).setOnAction((e) -> {
                if(enterPin.length() < 6) {enterPin += "0";}
                code.setText(enterPin);
        });
        
        loginStack = new StackPane(btnLogin, btnSetPin, btnEnterReset);
        logReset = new VBox(20, loginStack, btnReset);
        
        keyPad.add(logReset, 0, 4);
        keyPad.setAlignment(Pos.CENTER);

        GridPane.setColumnSpan(logReset, 5);
        GridPane.setHgrow(btnLogin, Priority.ALWAYS);
        BorderPane.setAlignment(keyPad, Pos.CENTER);
        login.setCenter(keyPad);

    }

    private void setB(BorderPane login) {
        
        create = new VBox(10);
        cancelResetStack = new StackPane(btnResetPin, btnCancelReset);
        create.getChildren().addAll(cancelResetStack);
        create.setAlignment(Pos.CENTER);
        login.setBottom(create);
    }

//--------------------------------------------------------------------------------
//---------------------------------PASSWORDS SCREEN -----------------------------
    private void setTop(BorderPane passcodes) {
        lblTitle = new Label("PassManager");
        lblTitle.setFont(Font.font("Cambria", 40));
        BorderPane.setAlignment(lblTitle, Pos.CENTER);
        passcodes.setTop(lblTitle);
    }

    private void setCenter(BorderPane passcodes) {
        link = new HBox();
        display = new GridPane();
        
        display.addRow(0, new Label("WEBSITE :"));
        display.addRow(1, txtWebsite);
        display.addRow(2, new Label("USERNAME :"));
        display.addRow(3, txtUser);
        display.addRow(4, new Label("PASSWORD :"));
        display.addRow(5, txtPass);
        display.setVgap(5);
        
        display.setAlignment(Pos.CENTER);
        display.setPadding(new Insets(30,10,10,10));
        link.getChildren().addAll(list, display);
        BorderPane.setAlignment(link, Pos.CENTER);
        passcodes.setCenter(link);
    }

    private void setBottom(BorderPane passcodes) {
        layout = new VBox(5);
        layout.getChildren().addAll(btnAdd, btnLogout);
        BorderPane.setAlignment(layout, Pos.CENTER);
        passcodes.setBottom(layout);
    }
    
//---------------------------------Splash Stuff------------------------------------
    private void setSplash(BorderPane splash)
    {
        splash.setCenter(splashStack);
        splashScreenIn.play();
        splashTitleInvisible.play();
    }
    
//--------------------------------------------------------------------------------    
//------------------------------ADDING A WEBSITE ----------------------------------

    
    
    
    private void sett(BorderPane adding) {
        lblTitle = new Label("PassManager");
        lblTitle.setFont(Font.font("Cambria", 40));
        BorderPane.setAlignment(lblTitle, Pos.CENTER);
        adding.setTop(lblTitle);
    }

    private void setc(BorderPane adding) {
        
        inputPane = new GridPane();
        inputPane.setAlignment(Pos.CENTER);
        
        inputPane.setHgap(10);
        inputPane.setVgap(10);       
        
        inputPane.addRow(0, new Label("Link Name: "), txtLink);
        inputPane.addRow(1, new Label("User Name: "), txtUserName);
        inputPane.addRow(2, new Label("Password: "), txtPassword);
        
        adding.setCenter(inputPane);    
        
        
    }

    private void setb(BorderPane adding) {
        website = new VBox(5);
        website.getChildren().addAll(lblPassSave, btnSave, btnCancel);
        website.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(website, Pos.CENTER);
        adding.setBottom(website);
    }
    
    private void resetPin(String codeOut) {
        enterPin = "";
        code.setText(codeOut);
        
    }
    
    private void resetPin() {
        enterPin = "";
        code.setText("");
        
    }
    
    private void addPassword() {

        String linkname = txtLink.getText();
        String userName = txtUserName.getText();
        String passcode = txtPassword.getText();

        txtLink.setText("");
        txtUserName.setText("");
        txtPassword.setText("");

        Password password = new Password(linkname, userName, passcode);

        passwords.add(password);
        list.getItems().add(password);
    }
    
    private void savePasswords(){
        
        for (int i = 0; i < passwords.size(); i++){
            passwordsJSON.add(passwords.get(i).toJSONObject());
        }
        
        JSONObject root = new JSONObject();

        root.put("passwords", passwordsJSON);
        
        saveToFile(root.toJSONString());
    }
    
    private void saveToFile(String jSONString) {

        try (PrintWriter writer = new PrintWriter(passwordSave)) {

            writer.println(jSONString);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }
    
    private void readPasswords() throws FileNotFoundException, ParseException {
        Scanner passIn = new Scanner(passwordSave);
        if(passIn.hasNext()){
            StringBuilder passBuilder = new StringBuilder();

            while(passIn.hasNextLine()){passBuilder.append(passIn.nextLine());}

            JSONParser parser = new JSONParser();

            JSONObject rootObject = (JSONObject) parser.parse(passBuilder.toString());

            JSONArray passArray = (JSONArray) rootObject.get("passwords");

            for (int i = 0; i < passArray.size(); i++)
            {
                JSONObject obj = (JSONObject) passArray.get(i);

                String linkName = (String) obj.get("linkName");
                String userName = (String) obj.get("userName");
                String passcode = (String) obj.get("passcode");

                passwords.add(new Password(linkName, userName, passcode));
            }
            for (int i = 0; i < passwords.size(); i++) {list.getItems().add(passwords.get(i));}
       }
    }
}

