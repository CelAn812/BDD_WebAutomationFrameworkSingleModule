package configuration.utilities;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name = "LogInInformation")
    public static Object[][] logInData(){
        return new Object[][]{
                {"OoompaLoompa","awwNoIDidItAgain"},//username password
                {"OhSnap","lifeManJustLife"},
                {"HereWeGoAgain","INeedMotivation"},
        };
    }

    @DataProvider(name = "RegistrationDataFromExcel")
    public static Object[][] getRegistrationDataFromExcel() {
        return ReadExcelDataDrivenApproach.getRegistrationTestData("AccountInfo");
    }

}
