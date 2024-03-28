package Pang_Dean_Project;




import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Login class provides the user to add a new code to login
 *
 * @author Timothy and Zachary
 */

public class Login{
    
    protected String code;
    
    public Login() throws FileNotFoundException {
        /**
         * Creates a Login object, used for accessing the program itself
         * @param pinRead accesses pin.txt
         * @param pinBoot scans from pin.txt when login object is created
         */
        File pinRead  = new File("pin.txt");;
        Scanner pinBoot = new Scanner(pinRead);;
        if (pinBoot.hasNext()){this.code = pinBoot.next();}
        else {this.code = "";}
    }

    public String getCode() {
        /**
         * @return code of login object
         */
        return code;
    }

    public void setCode(String code) {
        /**
         * Used for changing the code of the login object
         */
        this.code = code;
    }
    
    public void setPin(String pinOut)
    {
        /**
         * Used for writing to pin.txt when creating a passcode
         * @param pinWriterTemp used for writing to pin.txt
         * @param pinRead used for access the file pin.txt for saving to it
         * @param pinOut specifies what gets saved to pin.txt
         */
        File pinRead = new File("pin.txt");
        
        try (PrintWriter pinWriterTemp = new PrintWriter(pinRead))
        {
            pinWriterTemp.print(pinOut);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }
        
        this.setCode(pinOut);
    }
}